package pl.kacper.misterski.rangestats.feature.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.feature.settings.ui.settings.settings
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.addWeapon

internal const val ADD_WEAPON_RESULT_KEY = "add_weapon_result"

fun NavGraphBuilder.settingsFlow(
    navController: NavHostController, onBack: () -> Unit,
    onBottomNavigate: (BottomNavDestination) -> Unit
) {
    settings(
        onAddWeapon = {
            navController.navigate(AppRoutes.AddWeapon.route)
        },
        onBottomNavigate = onBottomNavigate,
    )

    addWeapon(
        onWeaponAdded = {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(ADD_WEAPON_RESULT_KEY, true)
            onBack()
        },
        onDismiss = onBack,
    )
}