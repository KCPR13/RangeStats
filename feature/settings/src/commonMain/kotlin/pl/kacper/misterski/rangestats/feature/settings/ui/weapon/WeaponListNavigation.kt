package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes

fun NavGraphBuilder.weaponList(
    onBack: () -> Unit,
    showAddWeapon: () -> Unit
) {
    composable(route = AppRoutes.WeaponList.route) {
        val viewModel = koinViewModel<WeaponListViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        WeaponListScreen(
            state = state,
            onAction = viewModel::onAction,
            onBack = onBack,
            showAddWeapon = showAddWeapon,
        )
    }
}
