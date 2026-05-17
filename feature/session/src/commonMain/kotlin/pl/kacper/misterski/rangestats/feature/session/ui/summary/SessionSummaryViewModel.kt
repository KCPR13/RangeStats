package pl.kacper.misterski.rangestats.feature.session.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.FinishSessionUseCase

class SessionSummaryViewModel(
    private val finishSession: FinishSessionUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SessionSummaryUiModel())
    val uiModel: StateFlow<SessionSummaryUiModel> = _uiModel.asStateFlow()

    //TODO why?
    private var onSaved: (() -> Unit)? = null

    fun init(sessionId: String) {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true, sessionId = sessionId) }
            finishSession(sessionId)
                .onSuccess { session ->
                    val zones = mutableMapOf<TargetZone, Int>()
                    session.shots.forEach { shot ->
                        zones[shot.zoneHit] = (zones[shot.zoneHit] ?: 0) + 1
                    }
                    val misses = zones[TargetZone.MISS] ?: 0
                    val hits = session.shots.size - misses
                    val durationMin = session.finishedAt?.let {
                        ((it - session.startedAt) / 60000).toInt() // TODO
                    } ?: 0
                    _uiModel.update {
                        it.copy(
                            isLoading = false,
                            locationName = session.locationName,
                            distanceMeters = session.distanceMeters,
                            durationMinutes = durationMin,
                            score = session.score ?: 0f,
                            totalHits = hits,
                            totalMisses = misses,
                            zoneDistribution = zones,
                        )
                    }
                }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }

    fun setOnSaved(callback: () -> Unit) {
        onSaved = callback
    }

    fun onAction(action: SessionSummaryAction) {
        when (action) {
            SessionSummaryAction.Save -> onSaved?.invoke()
            SessionSummaryAction.Share -> Unit
            SessionSummaryAction.Back -> Unit
        }
    }
}
