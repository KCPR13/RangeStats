package pl.kacper.misterski.rangestats.feature.session.ui.active

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.AnalyzeTargetUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.FinishSessionUseCase

class ActiveSessionViewModel(
    private val analyzeTarget: AnalyzeTargetUseCase,
    private val finishSession: FinishSessionUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ActiveSessionUiModel())
    val uiModel: StateFlow<ActiveSessionUiModel> = _uiModel.asStateFlow()

    //TODO K why?
    private var onSessionFinished: ((String) -> Unit)? = null

    private fun init(sessionId: String, locationName: String, distanceMeters: Int, caliber: String) { // TODO usage? onStart
        _uiModel.update {
            it.copy(
                sessionId = sessionId,
                locationName = locationName,
                distanceMeters = distanceMeters,
                caliber = caliber,
            )
        }
    }

    fun setOnSessionFinished(callback: (String) -> Unit) {
        onSessionFinished = callback
    }

    fun onAction(action: ActiveSessionAction) {
        when (action) {
            is ActiveSessionAction.AnalyzeTarget -> analyze(action.imageBytes)
            ActiveSessionAction.FinishSession -> finish()
            ActiveSessionAction.Back -> Unit
            ActiveSessionAction.OnStart -> init()
        }
    }

    private fun analyze(imageBytes: ByteArray) {
        val index = _uiModel.value.targetCount + 1
        _uiModel.update {
            it.copy(
                targetCount = index,
                targets = it.targets + TargetEntry(index, null, isAnalyzing = true),
            )
        }
        viewModelScope.launch {
            analyzeTarget(imageBytes)
                .onSuccess { result ->
                    val misses = result.zones[TargetZone.MISS] ?: 0
                    val hits = result.shotCount - misses
                    _uiModel.update { state ->
                        val updated = state.targets.map { t ->
                            if (t.index == index) t.copy(analysisResult = result, isAnalyzing = false) else t
                        }
                        state.copy(
                            targets = updated,
                            totalHits = state.totalHits + hits,
                            totalMisses = state.totalMisses + misses,
                            avgScore = updated
                                .mapNotNull { it.analysisResult?.score }
                                .average()
                                .toFloat()
                                .takeIf { it.isFinite() } ?: 0f,
                        )
                    }
                }
                .onFailure {
                    _uiModel.update { state ->
                        state.copy(
                            targets = state.targets.map { t ->
                                if (t.index == index) t.copy(isAnalyzing = false) else t
                            },
                        )
                    }
                }
        }
    }

    private fun finish() {
        val sessionId = _uiModel.value.sessionId
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            finishSession(sessionId)
                .onSuccess { onSessionFinished?.invoke(sessionId) }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }
}
