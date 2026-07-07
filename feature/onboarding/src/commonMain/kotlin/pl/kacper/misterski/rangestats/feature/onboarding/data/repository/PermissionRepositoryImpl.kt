package pl.kacper.misterski.rangestats.feature.onboarding.data.repository

import pl.kacper.misterski.rangestats.core.data.datasource.permission.PermissionDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus
import pl.kacper.misterski.rangestats.feature.onboarding.domain.repository.PermissionRepository

class PermissionRepositoryImpl(
    private val permissions: PermissionDataSource,
) : PermissionRepository {

    override suspend fun getStatus(permission: AppPermission): PermissionStatus =
        permissions.getStatus(permission)
}