package pl.kacper.misterski.rangestats.core.domain.models

data class Target(
    val id: String,
    val sessionId: String,
    val imageUrl: String,
    val analysisResult: AnalysisResult?,
    val shotCount: Int,
    val score: Float,
)
