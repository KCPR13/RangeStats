package pl.kacper.misterski.rangestats.feature.history.ui.list

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination

fun NavGraphBuilder.history(onBottomNavigate: (BottomNavDestination) -> Unit) {
    composable(route = AppRoutes.History.route) {
        val viewModel = koinViewModel<HistoryViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        HistoryScreen(
            state = state,
            onAction = viewModel::onAction,
            onBottomNavigate = onBottomNavigate,
        )
    }
}