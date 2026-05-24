package pl.kacper.misterski.rangestats.feature.history.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.DeleteSessionUseCase
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.GetSessionDetailUseCase

class SessionDetailViewModel(
    private val getSessionDetail: GetSessionDetailUseCase,
    private val deleteSession: DeleteSessionUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SessionDetailUiModel())
    val uiModel: StateFlow<SessionDetailUiModel> = _uiModel.asStateFlow()

    fun onAction(action: SessionDetailAction) {
        when (action) {
            is SessionDetailAction.Load -> load(action.sessionId)
            SessionDetailAction.Delete -> delete()
            SessionDetailAction.Back -> _uiModel.update { it.copy(navigateBack = true) }
            SessionDetailAction.NavigationHandled -> _uiModel.update { it.copy(navigateBack = false) }
        }
    }

    private fun load(sessionId: String) {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true, sessionId = sessionId) }
            getSessionDetail(sessionId)
                .onSuccess { session ->
                    val zoneCounts = mutableMapOf<TargetZone, Int>()
                    session.shots.forEach { shot ->
                        zoneCounts[shot.zoneHit] = (zoneCounts[shot.zoneHit] ?: 0) + 1
                    }
                    val misses = zoneCounts[TargetZone.MISS] ?: 0
                    val hits = session.shots.size - misses
                    val durationMin = session.finishedAt?.let {
                        ((it - session.startedAt) / 60000).toInt()
                    } ?: 0
                    val maxCount = zoneCounts.values.maxOrNull()?.coerceAtLeast(1) ?: 1
                    val zoneRows = listOf(
                        TargetZone.X, TargetZone.TEN, TargetZone.NINE,
                        TargetZone.EIGHT, TargetZone.SEVEN, TargetZone.SIX, TargetZone.MISS,
                    ).map { zone ->
                        val count = zoneCounts[zone] ?: 0
                        ZoneRowUiModel(
                            label = when (zone) {
                                TargetZone.X -> "X"
                                TargetZone.TEN -> "10"
                                TargetZone.NINE -> "9"
                                TargetZone.EIGHT -> "8"
                                TargetZone.SEVEN -> "7"
                                TargetZone.SIX -> "6"
                                TargetZone.MISS -> "✕"
                            },
                            count = count,
                            fraction = count.toFloat() / maxCount,
                            isMiss = zone == TargetZone.MISS,
                        )
                    }
                    _uiModel.update {
                        it.copy(
                            isLoading = false,
                            locationName = session.locationName,
                            distanceMeters = session.distanceMeters,
                            dateLabel = formatTimestamp(session.startedAt),
                            durationMinutes = durationMin,
                            score = session.score,
                            totalHits = hits,
                            totalMisses = misses,
                            shotCount = session.shots.size,
                            zoneRows = zoneRows,
                        )
                    }
                }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }

    private fun delete() {
        viewModelScope.launch {
            val sessionId = _uiModel.value.sessionId
            deleteSession(sessionId)
                .onSuccess { _uiModel.update { it.copy(navigateBack = true) } }
        }
    }

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
