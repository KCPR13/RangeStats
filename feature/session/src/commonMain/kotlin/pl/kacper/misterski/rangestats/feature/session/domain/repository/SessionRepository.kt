package pl.kacper.misterski.rangestats.feature.session.domain.repository

import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Shot

interface SessionRepository {
    suspend fun startSession(
        weaponName: String,
        locationName: String,
        distanceMeters: Int,
        targetType: TargetType,
    ): Session

    suspend fun addShots(shots: List<Shot>)

    suspend fun analyzeTarget(imageBytes: ByteArray): Result<AnalysisResult>

    suspend fun finishSession(sessionId: Long, score: Float?): Result<Session>

    suspend fun getSession(sessionId: Long): Session?
}
