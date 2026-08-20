package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import org.jetbrains.compose.resources.StringResource
import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggleOptionUiModel
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel

data class BallisticsUiModel(
    val presets: List<CaliberPresetUiItem> = emptyList(),
    val isLoading: Boolean = false,
    val muzzleVelocity: String = "",
    val bulletMass: String = "",
    val ballisticCoefficient: String = "",
    val bcModel: BcModel = BcModel.G1,
    val bcModelOptions: List<TacSegmentedToggleOptionUiModel> = emptyList(),
    val zeroRange: String = Constants.DEFAULT_ZERO_RANGE_METERS.toString(),
    val targetDistance: String = "",
    val scopeHeight: String = Constants.DEFAULT_SCOPE_HEIGHT_MM.toInt().toString(),
    val result: BallisticsResultUiModel? = null,
    val showEmptyFieldsError: Boolean = false,
    val calculationErrorRes: StringResource? = null,
    val isCalculateEnabled: Boolean = false,
) {
    data class CaliberPresetUiItem(val name: String, val selected: Boolean = false)

    data class BallisticsResultUiModel(
        val dropFormatRes: StringResource,
        val dropSignRes: StringResource,
        val dropDisplayValue: String,
        val dropAboveLoS: Boolean,
        val remainingVelocityMs: Int,
        val energyJoules: Int,
        val timeOfFlightDisplayValue: String,
    )
}
