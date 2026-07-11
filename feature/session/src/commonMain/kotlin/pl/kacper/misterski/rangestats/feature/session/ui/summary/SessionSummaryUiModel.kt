package pl.kacper.misterski.rangestats.feature.session.ui.summary

import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone

data class SessionSummaryUiModel(
    val sessionId: Long = 0,
    val locationName: String = "",
    val caliber: String = "",
    val distanceMeters: Int = 0,
    val durationMinutes: Int = 0,
    val score: Float? = null,
    val targetCount: Int = 0,
    val totalHits: Int = 0,
    val totalMisses: Int = 0,
    val zoneDistribution: Map<TargetZone, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val navigateToDashboard: Boolean = false,
)
