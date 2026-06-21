package pl.kacper.misterski.rangestats.feature.history.ui.detail

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.sessionDetail(
    onBack: () -> Unit,
) {
    composable(
        route = AppRoutes.SessionDetail.ROUTE,
        arguments = listOf(navArgument(AppRoutes.SessionDetail.ARG) { type = NavType.StringType }),
    ) { backStackEntry ->
        val sessionId = backStackEntry.arguments?.getString(AppRoutes.SessionDetail.ARG).orEmpty()
        val viewModel = koinViewModel<SessionDetailViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(sessionId) {
            viewModel.onAction(SessionDetailAction.Load(sessionId))
        }

        LaunchedEffect(state.navigateBack) {
            if (state.navigateBack) {
                viewModel.onAction(SessionDetailAction.NavigationHandled)
                onBack()
            }
        }

        SessionDetailScreen(
            state = state,
            onAction = { action ->
                if (action == SessionDetailAction.Back) onBack()
                else viewModel.onAction(action)
            },
        )
    }
}
