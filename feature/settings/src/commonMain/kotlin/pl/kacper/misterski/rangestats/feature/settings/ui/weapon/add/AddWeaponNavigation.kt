package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.bottomsheet.bottomSheet

fun NavGraphBuilder.addWeapon() {
    bottomSheet(route = AppRoutes.AddWeapon.route) {
        val viewModel = koinViewModel<AddWeaponViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()

        AddWeaponSheet(
            state = state,
            onAction = viewModel::onAction,
            onDismiss = { viewModel.onAction(AddWeaponAction.Dismiss) }, // TODO to po co ten dismiss jak jest on Action?
            modifier = Modifier.wrapContentSize()
        )
    }
}