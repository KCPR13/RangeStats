package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class AnalyzeTargetUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(imageBytes: ByteArray): Result<AnalysisResult> =
        sessionRepository.analyzeTarget(imageBytes)
}
