package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

data class CaliberPresetUiItem(val name: String)

data class BallisticsUiModel(
    val presets: List<CaliberPresetUiItem> = emptyList(),
    val selectedPresetIndex: Int? = null,
    val muzzleVelocity: String = "",
    val bulletMass: String = "",
    val ballisticCoefficient: String = "",
    val zeroRange: String = "100", // TODO hardcoded
    val targetDistance: String = "",
    val scopeHeight: String = "38",
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
