package pl.kacper.misterski.rangestats.feature.history.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.DeleteSessionUseCase
import pl.kacper.misterski.rangestats.feature.history.domain.usecase.GetSessionDetailUseCase
import kotlin.time.Instant

class SessionDetailViewModel(
    private val getSessionDetailUseCase: GetSessionDetailUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SessionDetailUiModel())
    val uiModel: StateFlow<SessionDetailUiModel> = _uiModel.asStateFlow()

    fun onAction(action: SessionDetailAction) {
        when (action) {
            is SessionDetailAction.Load -> load(action.sessionId)
            SessionDetailAction.Delete -> delete()
            SessionDetailAction.Back -> navigator.back()
        }
    }

    private fun load(sessionId: Long) {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true, sessionId = sessionId) }
            getSessionDetailUseCase(sessionId)
                .onSuccess { session ->
                    val zoneCounts = mutableMapOf<TargetZone, Int>()
                    session.shots.forEach { shot ->
                        zoneCounts[shot.zoneHit] = (zoneCounts[shot.zoneHit] ?: 0) + 1
                    }
                    val misses = zoneCounts[TargetZone.MISS] ?: 0
                    val hits = session.shots.size - misses
                    val durationMin = session.finishedAt?.let {
                        ((it - session.startedAt) / MILLIS_PER_MINUTE).toInt()
                    } ?: 0
                    val zoneRows = buildZoneRows(zoneCounts)
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
            deleteSessionUseCase(sessionId)
                .onSuccess { navigator.back() }
        }
    }

    private fun formatTimestamp(millis: Long): String {
        val date = Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.currentSystemDefault())
        return "%02d.%02d.%d".format(date.day, date.month.number, date.year)
    }

    private fun buildZoneRows(zoneCounts: Map<TargetZone, Int>): List<ZoneRowUiModel> {
        val maxCount = zoneCounts.values.maxOrNull()?.coerceAtLeast(1) ?: 1
        return ZONE_ORDER.map { zone ->
            val count = zoneCounts[zone] ?: 0
            ZoneRowUiModel(
                label = zone.toLabel(),
                count = count,
                fraction = count.toFloat() / maxCount,
                isMiss = zone == TargetZone.MISS,
            )
        }
    }

    companion object {
        private const val MILLIS_PER_MINUTE = 60000
        private val ZONE_ORDER = listOf(
            TargetZone.X, TargetZone.TEN, TargetZone.NINE,
            TargetZone.EIGHT, TargetZone.SEVEN, TargetZone.SIX, TargetZone.MISS,
        )
    }
}

//TODO DUPLICATED?
private fun TargetZone.toLabel(): String = when (this) {
    TargetZone.X -> "X"
    TargetZone.TEN -> "10"
    TargetZone.NINE -> "9"
    TargetZone.EIGHT -> "8"
    TargetZone.SEVEN -> "7"
    TargetZone.SIX -> "6"
    TargetZone.MISS -> "✕"
}
