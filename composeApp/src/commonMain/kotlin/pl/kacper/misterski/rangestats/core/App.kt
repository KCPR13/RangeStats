package pl.kacper.misterski.rangestats.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.core.navigation.NavigatorCommand
import pl.kacper.misterski.rangestats.core.ui.component.AnimatedLoader
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator.ballistics
import pl.kacper.misterski.rangestats.feature.history.ui.historyFlow
import pl.kacper.misterski.rangestats.feature.onboarding.ui.onboarding
import pl.kacper.misterski.rangestats.feature.session.ui.dashboard.dashboard
import pl.kacper.misterski.rangestats.feature.session.ui.sessionFlow
import pl.kacper.misterski.rangestats.feature.settings.ui.settingsFlow

@Composable
fun App() {
    RangeStatsTheme {
        val viewModel = koinViewModel<AppViewModel>()
        val navigator = koinInject<Navigator>()
        val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
        val navController = rememberNavController()

        LaunchedEffect(navController) {
            navigator.commands.onEach { command ->
                when (command) {
                    is NavigatorCommand.NavigateTo ->
                        navController.navigate(command.route.createRoute()) {
                            command.options.popUpTo?.let { target ->
                                popUpTo(target.route) { inclusive = command.options.popUpToInclusive }
                            }
                            launchSingleTop = command.options.launchSingleTop
                        }

                    NavigatorCommand.Back -> navController.navigateUp()
                }

            }.catch {
                // TODO logging
            }.collect()
        }

        AnimatedLoader(isLoading = startDestination is AppStartDestination.Loading) {
            val ready = startDestination as AppStartDestination.Ready
            AppNavHost(navController, ready.route)
        }
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        onboarding()
        dashboard()
        sessionFlow()
        historyFlow()
        settingsFlow()
        ballistics()
    }
}
