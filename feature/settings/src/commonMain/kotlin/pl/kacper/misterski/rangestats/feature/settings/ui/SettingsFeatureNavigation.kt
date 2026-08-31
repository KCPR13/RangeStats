package pl.kacper.misterski.rangestats.feature.settings.ui

import androidx.navigation.NavGraphBuilder
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.feature.settings.ui.settings.settings
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.addWeapon

fun NavGraphBuilder.settingsFlow(onBottomNavigate: (BottomNavDestination) -> Unit) {
    settings(onBottomNavigate = onBottomNavigate)
    addWeapon()
}