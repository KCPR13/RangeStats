package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.feature.ballistics.domain.exceptions.InvalidBallisticsInputException
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase.CalculateTrajectoryUseCase
import pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase.GetCaliberPresetsUseCase
import pl.kacper.misterski.rangestats.feature.ballistics.domain.validator.BallisticsInputValidator
import rangestats.feature.ballistics.generated.resources.Res
import rangestats.feature.ballistics.generated.resources.ballistics_error_generic

class BallisticsViewModel(
    private val getCaliberPresetsUseCase: GetCaliberPresetsUseCase,
    private val calculateTrajectoryUseCase: CalculateTrajectoryUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(BallisticsUiModel())
    val uiModel: StateFlow<BallisticsUiModel> = _uiModel.asStateFlow()

    private var domainPresets: List<CaliberPreset> = emptyList()
    private var selectedPresetName: String? = null

    fun onAction(action: BallisticsAction) {
        when (action) {
            BallisticsAction.OnStart -> loadPresets()
            is BallisticsAction.SelectPreset -> selectPreset(action.name)
            is BallisticsAction.SelectBcModel -> selectBcModel(action.index)

            is BallisticsAction.UpdateMuzzleVelocity -> _uiModel.update {
                it.copy(
                    muzzleVelocity = action.value,
                    result = null
                )
            }

            is BallisticsAction.UpdateBulletMass -> _uiModel.update {
                it.copy(
                    bulletMass = action.value,
                    result = null
                )
            }

            is BallisticsAction.UpdateBallisticCoefficient -> _uiModel.update {
                it.copy(
                    ballisticCoefficient = action.value,
                    result = null
                )
            }

            is BallisticsAction.UpdateZeroRange -> _uiModel.update {
                it.copy(
                    zeroRange = action.value,
                    result = null
                )
            }

            is BallisticsAction.UpdateTargetDistance -> _uiModel.update {
                it.copy(
                    targetDistance = action.value,
                    result = null
                )
            }

            is BallisticsAction.UpdateScopeHeight -> _uiModel.update {
                it.copy(
                    scopeHeight = action.value,
                    result = null
                )
            }

            BallisticsAction.Calculate -> calculate()
        }
    }

    private fun loadPresets() {
        viewModelScope.launch {
            domainPresets = getCaliberPresetsUseCase().catch { emit(emptyList()) }.first()
            val bcModelOptions = bcModelOptionsUiModel(_uiModel.value.bcModel)
            _uiModel.update {
                it.copy(presets = domainPresets.toPresetItems(selectedPresetName), bcModelOptions = bcModelOptions)
            }
        }
    }

    private fun selectPreset(name: String) {
        val preset = domainPresets.find { it.displayLabel == name } ?: return
        selectedPresetName = name
        viewModelScope.launch {
            val bcModelOptions = bcModelOptionsUiModel(BcModel.G1)
            _uiModel.update {
                it.copy(
                    presets = domainPresets.toPresetItems(name),
                    muzzleVelocity = preset.muzzleVelocityMs.toInt().toString(),
                    bulletMass = preset.bulletMassGrains.toInt().toString(),
                    ballisticCoefficient = "%.3f".format(preset.ballisticCoefficient),
                    bcModel = BcModel.G1,
                    bcModelOptions = bcModelOptions,
                    result = null,
                    showEmptyFieldsError = false,
                )
            }
        }
    }

    private fun selectBcModel(index: Int) {
        val model = BcModel.entries.getOrNull(index) ?: return
        viewModelScope.launch {
            val bcModelOptions = bcModelOptionsUiModel(model)
            _uiModel.update { it.copy(bcModel = model, bcModelOptions = bcModelOptions, result = null) }
        }
    }

    private fun calculate() {
        val state = _uiModel.value
        val input = buildInput(state) ?: return
        viewModelScope.launch {
            calculateTrajectoryUseCase(input)
                .catch { e ->
                    val errorRes = if (e is InvalidBallisticsInputException) {
                        e.toMessageRes()
                    } else {
                        Res.string.ballistics_error_generic
                    }
                    _uiModel.update { it.copy(calculationErrorRes = errorRes, result = null) }
                }
                .collect { result ->
                    _uiModel.update {
                        it.copy(
                            result = result.toUiModel(),
                            showEmptyFieldsError = false,
                            calculationErrorRes = null,
                        )
                    }
                }
        }
    }

    private fun buildInput(state: BallisticsUiModel): BallisticsInput? {
        val input = BallisticsInputValidator.validate(
            muzzleVelocity = state.muzzleVelocity,
            bulletMass = state.bulletMass,
            ballisticCoefficient = state.ballisticCoefficient,
            zeroRange = state.zeroRange,
            targetDistance = state.targetDistance,
            scopeHeight = state.scopeHeight,
            bcModel = state.bcModel,
        )
        if (input == null) {
            _uiModel.update { it.copy(showEmptyFieldsError = true) }
        }
        return input
    }

}
