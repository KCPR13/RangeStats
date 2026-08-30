package pl.kacper.misterski.rangestats.core.data.datasource.vision

import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult

interface VisionDataSource {
    suspend fun analyzeTarget(imageBytes: ByteArray): AnalysisResult
}
