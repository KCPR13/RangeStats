package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.datasource.permission.PermissionDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

class FakePermissionDataSource(
    private val defaultStatus: PermissionStatus = PermissionStatus.NOT_DETERMINED,
) : PermissionDataSource {

    private val overrides = mutableMapOf<AppPermission, PermissionStatus>()

    fun setStatus(permission: AppPermission, status: PermissionStatus) {
        overrides[permission] = status
    }

    override suspend fun getStatus(permission: AppPermission): PermissionStatus =
        overrides[permission] ?: defaultStatus
}
