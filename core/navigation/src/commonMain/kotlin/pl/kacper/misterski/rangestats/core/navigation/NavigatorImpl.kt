package pl.kacper.misterski.rangestats.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigatorImpl(
    private val scope: CoroutineScope,
) : Navigator {
    private val _commands = Channel<NavigatorCommand>()
    override val commands: Flow<NavigatorCommand> get() = _commands.receiveAsFlow()

    override fun navigateTo(route: AppRoutes, options: NavOptions) {
        scope.launch { _commands.send(NavigatorCommand.NavigateTo(route, options)) }
    }

    override fun navigateToBottomNav(destination: BottomNavDestination) {
        val route = when (destination) {
            BottomNavDestination.Home -> AppRoutes.Dashboard
            BottomNavDestination.History -> AppRoutes.History
            BottomNavDestination.NewSession -> AppRoutes.NewSession
            BottomNavDestination.Ballistics -> AppRoutes.Ballistics
            BottomNavDestination.Settings -> AppRoutes.Settings
        }
        val options = if (destination == BottomNavDestination.NewSession) {
            NavOptions()
        } else {
            NavOptions(popUpTo = AppRoutes.Dashboard, launchSingleTop = true)
        }
        navigateTo(route, options)
    }

    override fun back() {
        scope.launch { _commands.send(NavigatorCommand.Back) }
    }
}
