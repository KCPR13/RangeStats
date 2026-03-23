package pl.kacper.misterski.rangestats.core.data.datasource.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import org.koin.mp.KoinPlatform
import pl.kacper.misterski.rangestats.core.domain.enums.AppPermission
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

actual fun createPermissionDataSource(): PermissionDataSource = AndroidPermissionDataSource(
    context = KoinPlatform.getKoin().get(),
)

private class AndroidPermissionDataSource(
    private val context: Context,
) : PermissionDataSource {

    override suspend fun getStatus(permission: AppPermission): PermissionStatus {
        val manifestPermission = when (permission) {
            AppPermission.CAMERA -> Manifest.permission.CAMERA
            AppPermission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
        }

        return if (ContextCompat.checkSelfPermission(context, manifestPermission) == PackageManager.PERMISSION_GRANTED) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.NOT_DETERMINED
        }
    }
}
