package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class StartSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(
        weaponName: String,
        locationName: String,
        distanceMeters: Int,
        targetType: TargetType,
    ): Result<Session> = runCatching {
        sessionRepository.startSession(weaponName, locationName, distanceMeters, targetType)
    }
}
