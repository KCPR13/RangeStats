package pl.kacper.misterski.rangestats.core.navigation

import kotlinx.coroutines.flow.Flow

interface Navigator {
    val commands: Flow<NavigatorCommand>

    fun navigateTo(route: AppRoutes, options: NavOptions = NavOptions())

    fun navigateToBottomNav(destination: BottomNavDestination)

    fun back()
}
