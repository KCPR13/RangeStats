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

fun NavGraphBuilder.sessionSummary() {
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

        SessionSummaryScreen(
            state = state,
            onAction = viewModel::onAction,
        )
    }
}
