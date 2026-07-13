package pl.kacper.misterski.rangestats.feature.session.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetSessionUseCase

class SessionSummaryViewModel(
    private val getSessionUseCase: GetSessionUseCase,
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
                .onSuccess { session ->
                    val zones = mutableMapOf<TargetZone, Int>()
                    session.shots.forEach { shot ->
                        zones[shot.zoneHit] = (zones[shot.zoneHit] ?: 0) + 1
                    }
                    val misses = zones[TargetZone.MISS] ?: 0
                    val hits = session.shots.size - misses
                    val durationMin = session.finishedAt?.let {
                        ((it - session.startedAt) / 60000).toInt()
                    } ?: 0
                    _uiModel.update {
                        it.copy(
                            isLoading = false,
                            locationName = session.locationName,
                            distanceMeters = session.distanceMeters,
                            durationMinutes = durationMin,
                            score = session.score,
                            totalHits = hits,
                            totalMisses = misses,
                            targetCount = session.shots.size,
                            zoneDistribution = zones,
                        )
                    }
                }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }
}
