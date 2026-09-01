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

fun NavGraphBuilder.sessionDetail() {
    composable(
        route = AppRoutes.SessionDetail.ROUTE,
        arguments = listOf(navArgument(AppRoutes.SessionDetail.ARG) { type = NavType.LongType }),
    ) { backStackEntry ->
        val sessionId = backStackEntry.arguments?.getLong(AppRoutes.SessionDetail.ARG) ?: 0L
        val viewModel = koinViewModel<SessionDetailViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(sessionId) {
            viewModel.onAction(SessionDetailAction.Load(sessionId))
        }

        SessionDetailScreen(
            state = state,
            onAction = viewModel::onAction,
        )
    }
}
