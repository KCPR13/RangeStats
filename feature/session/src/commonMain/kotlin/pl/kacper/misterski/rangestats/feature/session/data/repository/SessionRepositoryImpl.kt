package pl.kacper.misterski.rangestats.feature.session.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.vision.VisionDataSource
import pl.kacper.misterski.rangestats.core.data.mapper.toEntity
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Shot
import pl.kacper.misterski.rangestats.feature.session.currentTimeMillis
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository
import kotlin.uuid.ExperimentalUuidApi

class SessionRepositoryImpl(
    private val sessionDataSource: SessionDataSource,
    private val visionDataSource: VisionDataSource,
) : SessionRepository {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun startSession(
        weaponId: String,
        locationName: String,
        distanceMeters: Int,
        targetType: TargetType,
    ): Session {
        val session = Session(
            weaponId = weaponId,
            locationName = locationName,
            distanceMeters = distanceMeters,
            targetType = targetType,
            shots = emptyList(),
            startedAt = currentTimeMillis(),
            finishedAt = null,
            score = null,
        )
        sessionDataSource.insertSession(session.toEntity())
        return session
    }

    override suspend fun addShot(shot: Shot) {
        sessionDataSource.insertShot(shot.toEntity())
    }

    override suspend fun analyzeTarget(imageBytes: ByteArray): Result<AnalysisResult> =
        runCatching { visionDataSource.analyzeTarget(imageBytes) }

    override suspend fun finishSession(sessionId: String): Session {
        val entity = sessionDataSource.getSessionById(sessionId)
            ?: error("Session not found: $sessionId") //TODO
        val shots = sessionDataSource.getShotsForSession(sessionId)
        val updated = entity.copy(finishedAt = currentTimeMillis())
        sessionDataSource.updateSession(updated)
        return updated.toDomain(shots)
    }

    override suspend fun getSession(sessionId: String): Session? {
        val entity = sessionDataSource.getSessionById(sessionId) ?: return null
        val shots = sessionDataSource.getShotsForSession(sessionId)
        return entity.toDomain(shots)
    }
}
