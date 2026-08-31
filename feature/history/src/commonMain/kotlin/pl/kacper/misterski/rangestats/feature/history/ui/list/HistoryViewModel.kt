package pl.kacper.misterski.rangestats.feature.history.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.GetSessionsUseCase

class HistoryViewModel(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(HistoryUiModel())
    val uiModel: StateFlow<HistoryUiModel> = _uiModel.asStateFlow()

    init {
        load()
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OpenDetail -> navigator.navigateTo(AppRoutes.SessionDetail(action.sessionId))
            is HistoryAction.OnBottomNavigate -> navigator.navigateToBottomNav(action.destination)
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

    private fun formatTimestamp(millis: Long): String {
        val date = Instant.fromEpochMilliseconds(millis)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return "%02d.%02d.%d".format(date.day, date.month.number, date.year)
    }
}
