package pl.kacper.misterski.rangestats.core.ui.util

import androidx.compose.runtime.Composable
import pl.kacper.misterski.rangestats.core.domain.enums.PermissionStatus

@Composable
expect fun rememberCameraPermissionRequester(
    onResult: (PermissionStatus) -> Unit,
): () -> Unit

@Composable
expect fun rememberLocationPermissionRequester(
    onResult: (PermissionStatus) -> Unit,
): () -> Unit
