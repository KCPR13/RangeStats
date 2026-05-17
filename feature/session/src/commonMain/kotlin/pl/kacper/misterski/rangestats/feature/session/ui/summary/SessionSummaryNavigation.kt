package pl.kacper.misterski.rangestats.feature.session.ui.summary

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

//TODO usage
fun NavGraphBuilder.sessionSummary(
    sessionId: String,
    onSaved: () -> Unit,
    onBack: () -> Unit,
) {
    composable(route = AppRoutes.SessionSummary.route) {
        val viewModel = koinViewModel<SessionSummaryViewModel>()
        viewModel.init(sessionId)
        viewModel.setOnSaved(onSaved)
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        SessionSummaryScreen(
            state = state,
            onAction = { action ->
                if (action == SessionSummaryAction.Back) onBack()
                else viewModel.onAction(action)
            },
        )
    }
}
