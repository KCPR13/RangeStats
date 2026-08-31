package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import pl.kacper.misterski.rangestats.core.navigation.BottomNavDestination

sealed class DashboardAction {
    data object OpenHistory : DashboardAction()
    data class OnBottomNavigate(val destination: BottomNavDestination) : DashboardAction()
}
