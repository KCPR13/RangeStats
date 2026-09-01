package pl.kacper.misterski.rangestats.feature.session.ui.new

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.newSession() {
    composable(route = AppRoutes.NewSession.route) {
        val viewModel = koinViewModel<NewSessionViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        NewSessionScreen(
            state = state,
            onAction = viewModel::onAction,
        )
    }
}
