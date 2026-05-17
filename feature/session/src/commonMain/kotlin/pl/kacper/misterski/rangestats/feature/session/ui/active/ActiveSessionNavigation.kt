package pl.kacper.misterski.rangestats.feature.session.ui.active

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.activeSession(
    onSessionFinished: (String) -> Unit,
    onBack: () -> Unit,
) {
    composable(route = AppRoutes.ActiveSession.route) {
        val viewModel = koinViewModel<ActiveSessionViewModel>()
        viewModel.setOnSessionFinished(onSessionFinished)
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        ActiveSessionScreen(
            state = state,
            onAction = { action ->
                if (action == ActiveSessionAction.Back) onBack()
                else viewModel.onAction(action)
            },
        )
    }
}
