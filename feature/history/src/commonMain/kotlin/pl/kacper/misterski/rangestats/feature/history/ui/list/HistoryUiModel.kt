package pl.kacper.misterski.rangestats.feature.history.ui.list

data class SessionListItem(
    val id: Long,
    val locationName: String,
    val distanceMeters: Int,
    val dateLabel: String,
    val shotCount: Int,
    val score: Float?,
)

data class HistoryUiModel(
    val isLoading: Boolean = true,
    val sessions: List<SessionListItem> = emptyList(),
    val navigateToDetail: Long? = null,
)
