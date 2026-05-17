package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class AnalyzeTargetUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(imageBytes: ByteArray): Result<AnalysisResult> =
        repository.analyzeTarget(imageBytes)
}
