package pl.kacper.misterski.rangestats.feature.history.ui.detail

data class ZoneRowUiModel(
    val label: String,
    val count: Int,
    val fraction: Float,
    val isMiss: Boolean,
)