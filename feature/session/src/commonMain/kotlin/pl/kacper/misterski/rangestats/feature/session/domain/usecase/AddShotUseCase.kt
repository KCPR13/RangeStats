package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import pl.kacper.misterski.rangestats.core.domain.models.Shot
import pl.kacper.misterski.rangestats.feature.session.currentTimeMillis
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class AddShotUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(sessionId: Long, result: AnalysisResult): Result<Unit> = runCatching {
        val shots = result.zones.flatMap { (zone, count) ->
            List(count) { Shot(id = 0, sessionId = sessionId, zoneHit = zone, timestamp = currentTimeMillis()) }
        }
        repository.addShots(shots)
    }
}
