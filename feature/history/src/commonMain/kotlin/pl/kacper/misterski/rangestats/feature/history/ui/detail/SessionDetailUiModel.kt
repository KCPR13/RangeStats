package pl.kacper.misterski.rangestats.feature.history.ui.detail

data class SessionDetailUiModel(
    val isLoading: Boolean = true,
    val sessionId: Long = 0,
    val locationName: String = "",
    val distanceMeters: Int = 0,
    val dateLabel: String = "",
    val durationMinutes: Int = 0,
    val score: Float? = null,
    val totalHits: Int = 0,
    val totalMisses: Int = 0,
    val shotCount: Int = 0,
    val zoneRows: List<ZoneRowUiModel> = emptyList(),
)
