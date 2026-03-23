package pl.kacper.misterski.rangestats.core.data.datasource.permission

import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

actual fun createPermissionDataSource(): PermissionDataSource = IosPermissionDataSource()

private class IosPermissionDataSource : PermissionDataSource {

    override suspend fun getStatus(permission: AppPermission): PermissionStatus = when (permission) {
        AppPermission.CAMERA -> {
            val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
            if (status == AVAuthorizationStatusAuthorized) PermissionStatus.GRANTED
            else PermissionStatus.NOT_DETERMINED
        }
        AppPermission.LOCATION -> {
            val status: CLAuthorizationStatus = CLLocationManager.authorizationStatus()
            if (status == kCLAuthorizationStatusAuthorizedWhenInUse ||
                status == kCLAuthorizationStatusAuthorizedAlways
            ) PermissionStatus.GRANTED
            else PermissionStatus.NOT_DETERMINED
        }
    }
}
