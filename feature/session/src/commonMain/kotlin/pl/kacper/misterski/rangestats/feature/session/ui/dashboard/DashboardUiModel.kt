package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import pl.kacper.misterski.rangestats.core.domain.models.Session

data class DashboardUiModel(
    val totalSessions: Int = 0,
    val avgScore: Float = 0f,
    val totalShots: Int = 0,
    val bestScore: Float = 0f,
    val lastSession: Session? = null,
    val recentScores: List<Float> = emptyList(),
    val isLoading: Boolean = false,
)
