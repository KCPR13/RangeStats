package pl.kacper.misterski.rangestats.feature.session.ui.summary

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.sessionSummary(
    onSaved: () -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = AppRoutes.SessionSummary("").route,
        arguments = listOf(navArgument(AppRoutes.SessionSummary.ARG) { type = NavType.StringType }),
    ) { backStackEntry ->
        val sessionId = backStackEntry.arguments?.getString(AppRoutes.SessionSummary.ARG).orEmpty()
        val viewModel = koinViewModel<SessionSummaryViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(sessionId) {
            viewModel.onAction(SessionSummaryAction.Load(sessionId))
        }

        LaunchedEffect(state.navigateToDashboard) {
            if (state.navigateToDashboard) {
                viewModel.onAction(SessionSummaryAction.NavigationHandled)
                onSaved()
            }
        }

        SessionSummaryScreen(
            state = state,
            onAction = { action ->
                if (action == SessionSummaryAction.Back) onBack()
                else viewModel.onAction(action)
            },
        )
    }
}
