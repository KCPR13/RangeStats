package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.bottom_sheet.bottomSheet

fun NavGraphBuilder.addWeapon(onBack: () -> Unit) {

    bottomSheet(route = AppRoutes.WeaponList.route) {
        val viewModel = koinViewModel<AddWeaponViewModel>()
        val state by viewModel.uiModel.collectAsStateWithLifecycle()
        AddWeaponSheet(
            state = state,
            onAction = viewModel::onAction,
            onDismiss = onBack,
            modifier = Modifier.wrapContentSize()
        )
    }
}
