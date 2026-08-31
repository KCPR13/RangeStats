package pl.kacper.misterski.rangestats.core.testing.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.navigation.NavOptions
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.core.navigation.NavigatorCommand

class FakeNavigator : Navigator {
    override val commands: Flow<NavigatorCommand> = emptyFlow()

    val navigatedTo = mutableListOf<Pair<AppRoutes, NavOptions>>()
    var backCallCount = 0
        private set

    override fun navigateTo(route: AppRoutes, options: NavOptions) {
        navigatedTo += route to options
    }

    override fun back() {
        backCallCount++
    }
}