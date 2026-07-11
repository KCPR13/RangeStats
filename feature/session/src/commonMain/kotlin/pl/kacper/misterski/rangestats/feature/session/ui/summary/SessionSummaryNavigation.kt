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
        route = AppRoutes.SessionSummary.ROUTE,
        arguments = listOf(navArgument(AppRoutes.SessionSummary.ARG) { type = NavType.LongType }),
    ) { backStackEntry ->
        val sessionId = backStackEntry.arguments?.getLong(AppRoutes.SessionSummary.ARG) ?: 0L
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
