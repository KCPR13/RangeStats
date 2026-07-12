package pl.kacper.misterski.rangestats.feature.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.feature.settings.ui.settings.settings
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.addWeapon
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.weaponList

fun NavGraphBuilder.settingsFlow(navController: NavHostController, onBack: () -> Unit) {
    settings(
        onNavigateToWeaponList = {
            navController.navigate(AppRoutes.WeaponList.route)
        },
    )

    weaponList(
        onBack = onBack,
        showAddWeapon = {
            navController.navigate(AppRoutes.AddWeapon.route)
        },
    )

    addWeapon(onBack = onBack)
}