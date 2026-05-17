package pl.kacper.misterski.rangestats.feature.session.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.feature.session.domain.repository.SessionRepository

class StartSessionUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(
        weaponId: String,
        locationName: String,
        distanceMeters: Int,
        targetType: TargetType,
    ): Result<Session> = runCatching {
        repository.startSession(weaponId, locationName, distanceMeters, targetType)
    }
}
