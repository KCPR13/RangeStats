package pl.kacper.misterski.rangestats.feature.onboarding.ui

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.ui.util.rememberCameraPermissionRequester
import pl.kacper.misterski.rangestats.core.ui.util.rememberLocationPermissionRequester
import pl.kacper.misterski.rangestats.core.ui.util.rememberOpenAppSettings

//TODO move to routes
const val ONBOARDING_ROUTE = "onboarding"

fun NavGraphBuilder.onboarding(onComplete: () -> Unit) {
    composable(route = ONBOARDING_ROUTE) {
        val viewModel = koinViewModel<OnboardingViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        val requestCamera = rememberCameraPermissionRequester { status ->
            viewModel.onAction(OnboardingAction.CameraPermissionResult(status))
        }
        val requestLocation = rememberLocationPermissionRequester { status ->
            viewModel.onAction(OnboardingAction.LocationPermissionResult(status))
        }
        val openSettings = rememberOpenAppSettings()

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.checkCameraPermission()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        LaunchedEffect(state.isCompleted) {
            if (state.isCompleted) onComplete()
        }

        OnboardingScreen(
            state = state,
            onAction = viewModel::onAction,
            requestCameraPermission = requestCamera,
            requestLocationPermission = requestLocation,
            openAppSettings = openSettings,
        )
    }
}
