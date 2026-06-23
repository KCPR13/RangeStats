package pl.kacper.misterski.rangestats.feature.session.ui.active

import pl.kacper.misterski.rangestats.core.domain.Constants

data class ActiveSessionUiModel(
    val sessionId: String = "",
    val locationName: String = "",
    val caliber: String = "",
    val distanceMeters: Int = Constants.DEFAULT_DISTANCE_METERS,
    val elapsedSeconds: Long = 0L,
    val targetCount: Int = 0,
    val avgScore: Float = 0f,
    val totalHits: Int = 0,
    val totalMisses: Int = 0,
    val targets: List<TargetEntry> = emptyList(),
    val isLoading: Boolean = false,
    val navigateToSummary: String? = null,
)
