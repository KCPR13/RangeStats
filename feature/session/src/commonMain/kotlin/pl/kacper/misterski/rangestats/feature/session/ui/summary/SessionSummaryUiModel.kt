package pl.kacper.misterski.rangestats.feature.session.ui.summary

data class SessionSummaryUiModel(
    val sessionId: Long = 0,
    val locationName: String = "",
    val ammoLabel: String = "",
    val distanceMeters: Int = 0,
    val durationMinutes: Int = 0,
    val scoreLabel: String = "—",
    val targetCount: Int = 0,
    val totalHits: Int = 0,
    val totalMisses: Int = 0,
    val zoneRows: List<ZoneRowUiModel> = emptyList(),
    val isLoading: Boolean = false,
) {
    data class ZoneRowUiModel(val label: String, val count: Int, val fraction: Float, val isMiss: Boolean)
}
