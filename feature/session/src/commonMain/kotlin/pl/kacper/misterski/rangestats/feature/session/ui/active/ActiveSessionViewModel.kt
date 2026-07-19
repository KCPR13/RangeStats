package pl.kacper.misterski.rangestats.feature.session.ui.active

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.AddShotUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.AnalyzeTargetUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.FinishSessionUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetSessionUseCase
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetWeaponByNameUseCase

class ActiveSessionViewModel(
    private val analyzeTargetUseCase: AnalyzeTargetUseCase,
    private val addShotUseCase: AddShotUseCase,
    private val finishSessionUseCase: FinishSessionUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val getWeaponByNameUseCase: GetWeaponByNameUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ActiveSessionUiModel())
    val uiModel: StateFlow<ActiveSessionUiModel> = _uiModel.asStateFlow()

    fun onAction(action: ActiveSessionAction) {
        when (action) {
            is ActiveSessionAction.AnalyzeTarget -> analyze(action.imageBytes)
            ActiveSessionAction.FinishSession -> finish()
            ActiveSessionAction.Back -> Unit
            is ActiveSessionAction.OnStart -> loadSession(action.sessionId)
            ActiveSessionAction.NavigationHandled -> _uiModel.update { it.copy(navigateToSummary = null) }
        }
    }

    private fun loadSession(sessionId: Long) {
        viewModelScope.launch {
            getSessionUseCase(sessionId).onSuccess { session ->
                val weapon = getWeaponByNameUseCase(session.weaponName).getOrNull()
                _uiModel.update { session.toUiModel(weapon) }
            }
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
            analyzeTargetUseCase(imageBytes)
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
                    addShotUseCase(_uiModel.value.sessionId, result)
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
        val state = _uiModel.value
        val hasAnalyzedTarget = state.targets.any { it.analysisResult != null }
        val score = state.avgScore.takeIf { hasAnalyzedTarget }
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true) }
            finishSessionUseCase(state.sessionId, score)
                .onSuccess { _uiModel.update { it.copy(isLoading = false, navigateToSummary = state.sessionId) } }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }
}
