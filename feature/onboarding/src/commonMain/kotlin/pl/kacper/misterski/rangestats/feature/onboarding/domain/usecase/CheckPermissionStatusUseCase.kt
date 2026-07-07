package pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase

import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.PermissionRepository

class CheckPermissionStatusUseCase(private val permissionRepository: PermissionRepository) {
    suspend operator fun invoke(permission: AppPermission): PermissionStatus = permissionRepository.getStatus(permission)
}