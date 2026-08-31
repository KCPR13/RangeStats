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

    override fun back() {
        scope.launch { _commands.send(NavigatorCommand.Back) }
    }
}
