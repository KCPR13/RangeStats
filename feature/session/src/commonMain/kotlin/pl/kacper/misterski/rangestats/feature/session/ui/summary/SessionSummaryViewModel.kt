package pl.kacper.misterski.rangestats.feature.session.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.domain.usecase.GetWeaponByNameUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetSessionUseCase

class SessionSummaryViewModel(
    private val getSessionUseCase: GetSessionUseCase,
    private val getWeaponByNameUseCase: GetWeaponByNameUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SessionSummaryUiModel())
    val uiModel: StateFlow<SessionSummaryUiModel> = _uiModel.asStateFlow()

    fun onAction(action: SessionSummaryAction) {
        when (action) {
            is SessionSummaryAction.Load -> load(action.sessionId)
            SessionSummaryAction.Save -> _uiModel.update { it.copy(navigateToDashboard = true) }
            SessionSummaryAction.NavigationHandled -> _uiModel.update { it.copy(navigateToDashboard = false) }
            SessionSummaryAction.Share -> Unit
            SessionSummaryAction.Back -> Unit
        }
    }

    private fun load(sessionId: Long) {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true, sessionId = sessionId) }
            getSessionUseCase(sessionId)
                .onSuccess { session -> // TODO K UI mapper standards
                    val zones = mutableMapOf<TargetZone, Int>()
                    session.shots.forEach { shot ->
                        zones[shot.zoneHit] = (zones[shot.zoneHit] ?: 0) + 1
                    }
                    val misses = zones[TargetZone.MISS] ?: 0
                    val hits = session.shots.size - misses
                    val durationMin = session.finishedAt?.let {
                        ((it - session.startedAt) / 60000).toInt()
                    } ?: 0
                    val weapon = getWeaponByNameUseCase(session.weaponName).firstOrNull()
                    val maxZoneCount = zones.values.maxOrNull()?.coerceAtLeast(1) ?: 1
                    val zoneRows = listOf(
                        TargetZone.X, TargetZone.TEN, TargetZone.NINE, TargetZone.EIGHT,
                        TargetZone.SEVEN, TargetZone.SIX, TargetZone.MISS,
                    ).map { zone ->
                        val count = zones[zone] ?: 0
                        val label = when (zone) {
                            TargetZone.X -> "X"
                            TargetZone.TEN -> "10"
                            TargetZone.NINE -> "9"
                            TargetZone.EIGHT -> "8"
                            TargetZone.SEVEN -> "7"
                            TargetZone.SIX -> "6"
                            TargetZone.MISS -> "✕"
                        }
                        SessionSummaryUiModel.ZoneRowUiModel(
                            label = label,
                            count = count,
                            fraction = count.toFloat() / maxZoneCount,
                            isMiss = zone == TargetZone.MISS,
                        )
                    }
                    _uiModel.update {
                        it.copy(
                            isLoading = false,
                            locationName = session.locationName,
                            ammoLabel = weapon?.ammunition?.displayLabel.orEmpty(),
                            distanceMeters = session.distanceMeters,
                            durationMinutes = durationMin,
                            scoreLabel = session.score?.let { score -> "${score.toInt()}%" } ?: "—",
                            totalHits = hits,
                            totalMisses = misses,
                            targetCount = session.shots.size,
                            zoneRows = zoneRows,
                        )
                    }
                }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }
}
