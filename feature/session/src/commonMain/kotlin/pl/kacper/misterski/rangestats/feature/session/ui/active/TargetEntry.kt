package pl.kacper.misterski.rangestats.feature.session.ui.active

import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult

data class TargetEntry(
    val index: Int,
    val analysisResult: AnalysisResult?,
    val isAnalyzing: Boolean,
)
