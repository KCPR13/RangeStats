package pl.kacper.misterski.rangestats.feature.history.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.GetSessionsUseCase

class HistoryViewModel(
    private val getSessionsUseCase: GetSessionsUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(HistoryUiModel())
    val uiModel: StateFlow<HistoryUiModel> = _uiModel.asStateFlow()

    init {
        load()
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OpenDetail -> _uiModel.update { it.copy(navigateToDetail = action.sessionId) }
            HistoryAction.NavigationHandled -> _uiModel.update { it.copy(navigateToDetail = null) }
        }
    }

    private fun load() {
        viewModelScope.launch {
            getSessionsUseCase()
                .onSuccess { sessions ->
                    _uiModel.update {
                        it.copy(
                            isLoading = false,
                            sessions = sessions
                                .sortedByDescending { s -> s.startedAt }
                                .map { s -> s.toListItem() },
                        )
                    }
                }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }

    private fun Session.toListItem() = SessionListItem(
        id = id,
        locationName = locationName,
        distanceMeters = distanceMeters,
        dateLabel = formatTimestamp(startedAt),
        shotCount = shots.size,
        score = score,
    )

    //TODO
    private fun formatTimestamp(millis: Long): String {
        val days = (millis / 86400000L).toInt()
        var remaining = days
        var year = 1970
        while (true) {
            val daysInYear = if (isLeapYear(year)) 366 else 365
            if (remaining < daysInYear) break
            remaining -= daysInYear
            year++
        }
        val monthDays = intArrayOf(31, if (isLeapYear(year)) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var month = 0
        while (month < 11 && remaining >= monthDays[month]) {
            remaining -= monthDays[month]
            month++
        }
        val day = remaining + 1
        return "%02d.%02d.%d".format(day, month + 1, year)
    }

    private fun isLeapYear(year: Int): Boolean =
        (year % 4 == 0 && year % 100 != 0) || year % 400 == 0
}
