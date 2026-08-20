package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import pl.kacper.misterski.rangestats.core.domain.models.Session

data class DashboardUiModel(
    val totalSessions: Int = 0,
    val avgScoreLabel: String = "—",
    val shotsLabel: String = "0",
    val bestScoreLabel: String = "—",
    val lastSession: Session? = null,
    val recentScores: List<Float> = emptyList(),
    val isLoading: Boolean = false,
) {
    data class StatDeltaUiModel(val text: String, val isPositive: Boolean)
}
