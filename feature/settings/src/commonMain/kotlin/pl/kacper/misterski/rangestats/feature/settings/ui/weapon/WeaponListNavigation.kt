package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.AddWeaponViewModel

fun NavGraphBuilder.weaponList(onBack: () -> Unit) {
    composable(route = AppRoutes.WeaponList.route) {
        val viewModel = koinViewModel<WeaponListViewModel>()
        val addViewModel = koinViewModel<AddWeaponViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        val addState by addViewModel.uiModel.collectAsStateWithLifecycle()
        WeaponListScreen(
            state = state,
            addState = addState,
            onAction = { action ->
                if (action == WeaponListAction.HideAddSheet) {
                    viewModel.reload()
                }
                viewModel.onAction(action)
            },
            onAddAction = addViewModel::onAction,
            onBack = onBack,
        )
    }
}
