package pl.kacper.misterski.rangestats.feature.onboarding.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel

//TODO move to routes
const val ONBOARDING_ROUTE = "onboarding"

fun NavGraphBuilder.onboarding(onComplete: () -> Unit) {
    composable(route = ONBOARDING_ROUTE) {
        val viewModel = koinViewModel<OnboardingViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(state.isCompleted) {
            if (state.isCompleted) onComplete()
        }

        OnboardingScreen(
            state = state,
            onAction = viewModel::onAction,
        )
    }
}
