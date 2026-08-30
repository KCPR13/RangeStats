package pl.kacper.misterski.rangestats.core.ui.util

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

@Composable
actual fun rememberCameraPermissionRequester(onResult: (PermissionStatus) -> Unit): () -> Unit {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                onResult(PermissionStatus.GRANTED)
            } else {
                val activity = context as? ComponentActivity
                val shouldShowRationale =
                    activity?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(it, Manifest.permission.CAMERA)
                    } ?: false
                onResult(if (shouldShowRationale) PermissionStatus.DENIED else PermissionStatus.PERMANENTLY_DENIED)
            }
        }
    return remember { { launcher.launch(Manifest.permission.CAMERA) } }
}

@Composable
actual fun rememberLocationPermissionRequester(onResult: (PermissionStatus) -> Unit): () -> Unit {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                onResult(PermissionStatus.GRANTED)
            } else {
                val activity = context as? ComponentActivity
                val shouldShowRationale =
                    activity?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                        )
                    } ?: false
                onResult(if (shouldShowRationale) PermissionStatus.DENIED else PermissionStatus.PERMANENTLY_DENIED)
            }
        }
    return remember { { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) } }
}
