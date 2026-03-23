package pl.kacper.misterski.rangestats.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.darwin.NSObject
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCameraPermissionRequester(
    onResult: (PermissionStatus) -> Unit,
): () -> Unit {
    val scope = rememberCoroutineScope()
    return remember(onResult) {
        {
            scope.launch {
                val granted = suspendCancellableCoroutine { cont ->
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                        cont.resume(granted)
                    }
                }
                // On iOS the system dialog shows only once — any denial is permanent
                onResult(if (granted) PermissionStatus.GRANTED else PermissionStatus.PERMANENTLY_DENIED)
            }
        }
    }
}

@Composable
actual fun rememberLocationPermissionRequester(
    onResult: (PermissionStatus) -> Unit,
): () -> Unit {
    val scope = rememberCoroutineScope()
    return remember(onResult) {
        {
            scope.launch {
                val status = suspendCancellableCoroutine { cont ->
                    val manager = CLLocationManager()
                    val delegate = LocationPermissionDelegate(
                        onStatus = { cont.resume(it) },
                        manager = manager,
                    )
                    manager.delegate = delegate
                    manager.requestWhenInUseAuthorization()
                }
                onResult(status)
            }
        }
    }
}

private class LocationPermissionDelegate(
    private val onStatus: (PermissionStatus) -> Unit,
    private val manager: CLLocationManager,
) : NSObject(), CLLocationManagerDelegateProtocol {

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        val status: CLAuthorizationStatus = manager.authorizationStatus()
        val result = when {
            status == kCLAuthorizationStatusAuthorizedWhenInUse ||
            status == kCLAuthorizationStatusAuthorizedAlways -> PermissionStatus.GRANTED
            status == kCLAuthorizationStatusDenied -> PermissionStatus.PERMANENTLY_DENIED
            else -> PermissionStatus.DENIED
        }
        onStatus(result)
        manager.delegate = null
    }
}
