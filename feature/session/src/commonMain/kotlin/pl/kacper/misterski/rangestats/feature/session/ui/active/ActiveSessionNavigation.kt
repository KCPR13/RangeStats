package pl.kacper.misterski.rangestats.feature.session.ui.active

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.activeSession(
    onSessionFinished: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = AppRoutes.ActiveSession("").route,
        arguments = listOf(navArgument(AppRoutes.ActiveSession.ARG) { type = NavType.StringType }),
    ) { backStackEntry ->
        val sessionId = backStackEntry.arguments?.getString(AppRoutes.ActiveSession.ARG).orEmpty()
        val viewModel = koinViewModel<ActiveSessionViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(sessionId) {
            viewModel.onAction(ActiveSessionAction.OnStart(sessionId))
        }

        LaunchedEffect(state.navigateToSummary) {
            val summaryId = state.navigateToSummary ?: return@LaunchedEffect
            viewModel.onAction(ActiveSessionAction.NavigationHandled)
            onSessionFinished(summaryId)
        }

        ActiveSessionScreen(
            state = state,
            onAction = { action ->
                if (action == ActiveSessionAction.Back) onBack()
                else viewModel.onAction(action)
            },
        )
    }
}
