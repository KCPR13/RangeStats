package pl.kacper.misterski.rangestats.feature.onboarding.domain.repository

import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

interface PermissionRepository {
    suspend fun getStatus(permission: AppPermission): PermissionStatus
}