package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.settings() {
    composable(route = AppRoutes.Settings.route) {
        val viewModel = koinViewModel<SettingsViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        SettingsScreen(
            model = state,
            onAction = viewModel::onAction,
            onAddWeapon = { viewModel.onAction(SettingsAction.AddWeapon) },
            onBottomNavigate = { viewModel.onAction(SettingsAction.OnBottomNavigate(it)) },
        )
    }
}
