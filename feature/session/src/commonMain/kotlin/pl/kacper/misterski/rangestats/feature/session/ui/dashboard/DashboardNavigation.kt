package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination

//TODO usage
fun NavGraphBuilder.dashboard(
    onNewSession: () -> Unit,
    onOpenHistory: () -> Unit,
    onNavigate: (BottomNavDestination) -> Unit,
) {
    composable(route = AppRoutes.Dashboard.route) {
        val viewModel = koinViewModel<DashboardViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        DashboardScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    DashboardAction.NewSession -> onNewSession()
                    DashboardAction.OpenHistory -> onOpenHistory()
                    DashboardAction.OpenSettings -> onNavigate(BottomNavDestination.Settings)
                }
            },
            onNavigate = onNavigate,
        )
    }
}
