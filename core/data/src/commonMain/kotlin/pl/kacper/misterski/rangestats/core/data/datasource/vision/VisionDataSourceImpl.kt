package pl.kacper.misterski.rangestats.core.data.datasource.vision

import pl.kacper.misterski.rangestats.core.data.remote.AnthropicVisionApiService
import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult

class VisionDataSourceImpl(
    private val apiService: AnthropicVisionApiService,
) : VisionDataSource {
    override suspend fun analyzeTarget(imageBytes: ByteArray): AnalysisResult = apiService.analyzeTarget(imageBytes)
}
