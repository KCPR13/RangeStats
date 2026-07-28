package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import androidx.compose.runtime.getValue
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.feature.settings.ui.ADD_WEAPON_RESULT_KEY

fun NavGraphBuilder.settings(onAddWeapon: () -> Unit,
                             onBottomNavigate: (BottomNavDestination) -> Unit,) {
    composable(route = AppRoutes.Settings.route) { entry ->
        val viewModel = koinViewModel<SettingsViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        val weaponAdded by entry.savedStateHandle
            .getStateFlow(ADD_WEAPON_RESULT_KEY, false)
            .collectAsStateWithLifecycle()

        LaunchedEffect(weaponAdded) {
            if (weaponAdded) {
                entry.savedStateHandle[ADD_WEAPON_RESULT_KEY] = false
                viewModel.onAction(SettingsAction.OnStart)
            }
        }

        SettingsScreen(
            model = state,
            onAction = viewModel::onAction,
            onAddWeapon = onAddWeapon,
            onBottomNavigate = onBottomNavigate,
        )
    }
}
