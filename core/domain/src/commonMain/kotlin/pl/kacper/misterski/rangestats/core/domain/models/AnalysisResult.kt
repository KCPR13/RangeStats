package pl.kacper.misterski.rangestats.core.domain.models

import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone

data class AnalysisResult(
    val shotCount: Int,
    val zones: Map<TargetZone, Int>,
    val score: Float,
)
