package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsInput
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase.CalculateTrajectoryUseCase
import pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase.GetCaliberPresetsUseCase

class BallisticsViewModel(
    private val getCaliberPresets: GetCaliberPresetsUseCase,
    private val calculateTrajectory: CalculateTrajectoryUseCase,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(BallisticsUiModel())
    val uiModel: StateFlow<BallisticsUiModel> = _uiModel.asStateFlow()

    private var domainPresets: List<CaliberPreset> = emptyList()

    init {
        domainPresets = getCaliberPresets()
        _uiModel.update { it.copy(presets = domainPresets.map { p -> CaliberPresetUiItem(p.name) }) }
    }

    fun onAction(action: BallisticsAction) {
        when (action) {
            is BallisticsAction.SelectPreset -> selectPreset(action.index)
            is BallisticsAction.UpdateMuzzleVelocity -> _uiModel.update { it.copy(muzzleVelocity = action.value, result = null) }
            is BallisticsAction.UpdateBulletMass -> _uiModel.update { it.copy(bulletMass = action.value, result = null) }
            is BallisticsAction.UpdateBallisticCoefficient -> _uiModel.update { it.copy(ballisticCoefficient = action.value, result = null) }
            is BallisticsAction.UpdateZeroRange -> _uiModel.update { it.copy(zeroRange = action.value, result = null) }
            is BallisticsAction.UpdateTargetDistance -> _uiModel.update { it.copy(targetDistance = action.value, result = null) }
            is BallisticsAction.UpdateScopeHeight -> _uiModel.update { it.copy(scopeHeight = action.value, result = null) }
            BallisticsAction.Calculate -> calculate()
        }
    }

    private fun selectPreset(index: Int) {
        val preset = domainPresets.getOrNull(index) ?: return
        _uiModel.update {
            it.copy(
                selectedPresetIndex = index,
                muzzleVelocity = preset.muzzleVelocityMs.toInt().toString(),
                bulletMass = preset.bulletMassGrains.toInt().toString(),
                ballisticCoefficient = "%.3f".format(preset.ballisticCoefficient),
                result = null,
                showEmptyFieldsError = false,
            )
        }
    }

    private fun calculate() {
        val state = _uiModel.value
        val input = buildInput(state) ?: return
        calculateTrajectory(input)
            .onSuccess { result ->
                val dropSign = if (result.dropMm >= 0) SIGN_UP else SIGN_DOWN
                _uiModel.update {
                    it.copy(
                        result = BallisticsResultUiModel(
                            dropLabel = "$dropSign ${formatMm(kotlin.math.abs(result.dropMm))}",
                            dropAboveLoS = result.dropMm >= 0,
                            velocityLabel = "${result.remainingVelocityMs.toInt()}$UNIT_MS",
                            energyLabel = "${result.energyJoules.toInt()}$UNIT_JOULES",
                            timeOfFlightLabel = formatSeconds(result.timeOfFlightSec),
                        ),
                        showEmptyFieldsError = false,
                        calculationError = null,
                    )
                }
            }
            .onFailure { e ->
                _uiModel.update { it.copy(calculationError = e.message, result = null) }
            }
    }

    private fun buildInput(state: BallisticsUiModel): BallisticsInput? {
        val v0 = state.muzzleVelocity.toDoubleOrNull()
        val mass = state.bulletMass.toDoubleOrNull()
        val bc = state.ballisticCoefficient.toDoubleOrNull()
        val zero = state.zeroRange.toIntOrNull()
        val dist = state.targetDistance.toIntOrNull()
        val scope = state.scopeHeight.toDoubleOrNull()

        if (v0 == null || mass == null || bc == null || zero == null || dist == null || scope == null) {
            _uiModel.update { it.copy(showEmptyFieldsError = true) }
            return null
        }
        return BallisticsInput(
            muzzleVelocityMs = v0,
            bulletMassGrains = mass,
            ballisticCoefficient = bc,
            zeroRangeMeters = zero,
            targetDistanceMeters = dist,
            scopeHeightMm = scope,
        )
    }

    private fun formatMm(mm: Double): String = when {
        mm >= MM_PER_METER -> "${FORMAT_ONE_DECIMAL.format(mm / MM_PER_METER)}$UNIT_METERS"
        else -> "${mm.toInt()}$UNIT_MM"
    }

    private fun formatSeconds(sec: Double): String = "${"%.3f".format(sec)} s"

    companion object {
        private const val SIGN_UP = "↑"
        private const val SIGN_DOWN = "↓"
        private const val UNIT_MS = " m/s"
        private const val UNIT_JOULES = " J"
        private const val MM_PER_METER = 1000.0
        private const val FORMAT_ONE_DECIMAL = "%.1f"
        private const val UNIT_METERS = " m"
        private const val UNIT_MM = " mm"
    }

}
