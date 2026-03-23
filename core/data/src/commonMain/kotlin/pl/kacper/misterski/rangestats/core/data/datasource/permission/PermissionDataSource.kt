package pl.kacper.misterski.rangestats.core.data.datasource.permission

import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

interface PermissionDataSource {
    suspend fun getStatus(permission: AppPermission): PermissionStatus
}
