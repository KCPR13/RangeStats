package pl.kacper.misterski.rangestats.feature.history.ui.list

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination

fun NavGraphBuilder.history(
    onOpenDetail: (Long) -> Unit,
    onNavigate: (BottomNavDestination) -> Unit,
) {
    composable(route = AppRoutes.History.route) {
        val viewModel = koinViewModel<HistoryViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        LaunchedEffect(state.navigateToDetail) {
            val sessionId = state.navigateToDetail
            if (sessionId != null) {
                viewModel.onAction(HistoryAction.NavigationHandled)
                onOpenDetail(sessionId)
            }
        }

        HistoryScreen(
            state = state,
            onAction = viewModel::onAction,
            onNavigate = onNavigate,
        )
    }
}
