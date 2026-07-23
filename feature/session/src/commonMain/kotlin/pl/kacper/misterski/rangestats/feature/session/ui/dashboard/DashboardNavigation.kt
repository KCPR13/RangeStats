package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination

fun NavGraphBuilder.dashboard(
    onOpenHistory: () -> Unit,
    onBottomNavigate: (BottomNavDestination) -> Unit,
) {
    composable(route = AppRoutes.Dashboard.route) {
        val viewModel = koinViewModel<DashboardViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        DashboardScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    DashboardAction.OpenHistory -> onOpenHistory()
                }
            },
            onBottomNavigate = onBottomNavigate,
        )
    }
}
