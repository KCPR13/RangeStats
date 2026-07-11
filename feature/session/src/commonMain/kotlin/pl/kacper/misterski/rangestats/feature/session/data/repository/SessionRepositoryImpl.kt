package pl.kacper.misterski.rangestats.feature.session.data.repository

import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.datasource.session.SessionDataSource
import pl.kacper.misterski.rangestats.core.data.datasource.vision.VisionDataSource
import pl.kacper.misterski.rangestats.core.domain.exceptions.SessionNotFoundException
import pl.kacper.misterski.rangestats.core.data.mapper.toEntity
import pl.kacper.misterski.rangestats.core.data.mapper.toDomain
import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Shot
import pl.kacper.misterski.rangestats.feature.session.currentTimeMillis
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val sessionDataSource: SessionDataSource,
    private val visionDataSource: VisionDataSource,
) : SessionRepository {

    override suspend fun startSession(
        weaponId: String,
        locationName: String,
        distanceMeters: Int,
        targetType: TargetType,
    ): Session {
        val entity = SessionEntity(
            weaponId = weaponId,
            locationName = locationName,
            distanceMeters = distanceMeters,
            targetType = targetType.name,
            startedAt = currentTimeMillis(),
            finishedAt = null,
            score = null,
        )
        val id = sessionDataSource.insertSession(entity)
        return entity.copy(id = id).toDomain(emptyList())
    }

    override suspend fun addShot(shot: Shot) {
        sessionDataSource.insertShot(shot.toEntity())
    }

    override suspend fun analyzeTarget(imageBytes: ByteArray): Result<AnalysisResult> =
        runCatching { visionDataSource.analyzeTarget(imageBytes) }

    override suspend fun finishSession(sessionId: Long): Result<Session> = runCatching {
        val entity = sessionDataSource.getSessionById(sessionId)
            ?: throw SessionNotFoundException(sessionId)
        val shots = sessionDataSource.getShotsForSession(sessionId)
        val updated = entity.copy(finishedAt = currentTimeMillis())
        sessionDataSource.updateSession(updated)
        updated.toDomain(shots)
    }

    override suspend fun getSession(sessionId: Long): Session? {
        val entity = sessionDataSource.getSessionById(sessionId) ?: return null
        val shots = sessionDataSource.getShotsForSession(sessionId)
        return entity.toDomain(shots)
    }
}
