package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination

fun NavGraphBuilder.ballistics(
    onNavigate: (BottomNavDestination) -> Unit,
) {
    composable(route = AppRoutes.Ballistics.route) {
        val viewModel = koinViewModel<BallisticsViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        BallisticsScreen(
            state = state,
            onAction = viewModel::onAction,
            onNavigate = onNavigate,
        )
    }
}
