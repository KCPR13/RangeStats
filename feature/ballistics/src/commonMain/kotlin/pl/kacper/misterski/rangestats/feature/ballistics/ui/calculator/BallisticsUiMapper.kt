package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggleOptionUiModel
import pl.kacper.misterski.rangestats.feature.ballistics.domain.exceptions.InvalidBallisticsInputException
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BallisticsResult
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import rangestats.feature.ballistics.generated.resources.Res
import rangestats.feature.ballistics.generated.resources.ballistics_bc_model_g1
import rangestats.feature.ballistics.generated.resources.ballistics_bc_model_g7
import rangestats.feature.ballistics.generated.resources.ballistics_error_invalid_bc
import rangestats.feature.ballistics.generated.resources.ballistics_error_invalid_bullet_mass
import rangestats.feature.ballistics.generated.resources.ballistics_error_invalid_distance
import rangestats.feature.ballistics.generated.resources.ballistics_error_invalid_muzzle_velocity
import rangestats.feature.ballistics.generated.resources.ballistics_error_invalid_zero_range
import rangestats.feature.ballistics.generated.resources.ballistics_result_drop_m_format
import rangestats.feature.ballistics.generated.resources.ballistics_result_drop_mm_format
import rangestats.feature.ballistics.generated.resources.ballistics_sign_down
import rangestats.feature.ballistics.generated.resources.ballistics_sign_up
import kotlin.math.abs

fun InvalidBallisticsInputException.toMessageRes(): StringResource = when (this) {
    InvalidBallisticsInputException.InvalidMuzzleVelocity -> Res.string.ballistics_error_invalid_muzzle_velocity
    InvalidBallisticsInputException.InvalidBallisticCoefficient -> Res.string.ballistics_error_invalid_bc
    InvalidBallisticsInputException.InvalidBulletMass -> Res.string.ballistics_error_invalid_bullet_mass
    InvalidBallisticsInputException.InvalidZeroRange -> Res.string.ballistics_error_invalid_zero_range
    InvalidBallisticsInputException.InvalidTargetDistance -> Res.string.ballistics_error_invalid_distance
}

suspend fun bcModelOptionsUiModel(selected: BcModel): List<TacSegmentedToggleOptionUiModel> =
    BcModel.entries.map { model ->
        TacSegmentedToggleOptionUiModel(label = getString(model.toLabelRes()), selected = model == selected)
    }

private fun BcModel.toLabelRes(): StringResource = when (this) {
    BcModel.G1 -> Res.string.ballistics_bc_model_g1
    BcModel.G7 -> Res.string.ballistics_bc_model_g7
}

private const val MM_PER_METER = 1000.0

fun BallisticsResult.toUiModel(): BallisticsUiModel.BallisticsResultUiModel {
    val aboveLoS = dropMm >= 0
    val absMm = abs(dropMm)
    val (formatRes, displayValue) = if (absMm >= MM_PER_METER) {
        Res.string.ballistics_result_drop_m_format to "%.1f".format(absMm / MM_PER_METER)
    } else {
        Res.string.ballistics_result_drop_mm_format to absMm.toInt().toString()
    }
    return BallisticsUiModel.BallisticsResultUiModel(
        dropFormatRes = formatRes,
        dropSignRes = if (aboveLoS) Res.string.ballistics_sign_up else Res.string.ballistics_sign_down,
        dropDisplayValue = displayValue,
        dropAboveLoS = aboveLoS,
        remainingVelocityMs = remainingVelocityMs.toInt(),
        energyJoules = energyJoules.toInt(),
        timeOfFlightDisplayValue = "%.3f".format(timeOfFlightSec),
    )
}

fun List<CaliberPreset>.toPresetItems(selectedName: String?): List<BallisticsUiModel.CaliberPresetUiItem> =
    mapIndexed { index, preset ->
        BallisticsUiModel.CaliberPresetUiItem(
            name = preset.displayLabel,
            selected = if (selectedName == null) index == 0 else preset.displayLabel == selectedName,
        )
    }
