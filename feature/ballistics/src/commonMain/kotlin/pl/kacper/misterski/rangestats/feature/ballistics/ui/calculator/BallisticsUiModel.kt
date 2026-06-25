package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import pl.kacper.misterski.rangestats.core.domain.Constants

data class CaliberPresetUiItem(val name: String)

data class BallisticsUiModel(
    val presets: List<CaliberPresetUiItem> = emptyList(),
    val selectedPresetIndex: Int? = null,
    val muzzleVelocity: String = "",
    val bulletMass: String = "",
    val ballisticCoefficient: String = "",
    val zeroRange: String = Constants.DEFAULT_ZERO_RANGE_METERS.toString(),
    val targetDistance: String = "",
    val scopeHeight: String = Constants.DEFAULT_SCOPE_HEIGHT_MM.toInt().toString(),
    val result: BallisticsResultUiModel? = null,
    val showEmptyFieldsError: Boolean = false,
    val calculationError: String? = null,
)

data class BallisticsResultUiModel(
    val dropLabel: String,
    val dropAboveLoS: Boolean,
    val velocityLabel: String,
    val energyLabel: String,
    val timeOfFlightLabel: String,
)
