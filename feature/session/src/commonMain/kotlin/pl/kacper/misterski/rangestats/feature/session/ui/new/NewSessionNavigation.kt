package pl.kacper.misterski.rangestats.feature.session.ui.new

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.newSession(
    onSessionStarted: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(route = AppRoutes.NewSession.route) {
        val viewModel = koinViewModel<NewSessionViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(state.navigateToActiveSession) {
            val sessionId = state.navigateToActiveSession ?: return@LaunchedEffect
            viewModel.onAction(NewSessionAction.NavigationHandled)
            onSessionStarted(sessionId)
        }

        NewSessionScreen(
            state = state,
            onAction = { action ->
                if (action == NewSessionAction.Back) onBack()
                else viewModel.onAction(action)
            },
        )
    }
}
