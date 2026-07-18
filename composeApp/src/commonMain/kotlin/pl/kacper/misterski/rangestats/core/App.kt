package pl.kacper.misterski.rangestats.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.component.AnimatedLoader
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
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
        val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
        val navController = rememberNavController()

        AnimatedLoader(isLoading = startDestination is AppStartDestination.Loading) {
            val ready = startDestination as AppStartDestination.Ready
            AppNavHost(navController, ready.route)
        }
    }
}

@Composable
private fun AppNavHost(navController: NavHostController, startDestination: String) {
    val onBack: () -> Unit = { navController.navigateUp() }
    val onNavigate: (BottomNavDestination) -> Unit = { destination -> navController.navigateBottomNav(destination) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        onboarding(
            onComplete = {
                navController.navigate(AppRoutes.Dashboard.route) {
                    popUpTo(AppRoutes.Onboarding.route) { inclusive = true }
                }
            },
            onAddWeapon = {
                navController.navigate(AppRoutes.AddWeapon.route)
            },
        )

        dashboard(
            onOpenHistory = {
                navController.navigate(AppRoutes.History.route)
            },
            onNavigate = onNavigate,
        )

        sessionFlow(navController, onBack)
        historyFlow(navController, onBack, onNavigate)
        settingsFlow(navController, onBack)

        ballistics(onNavigate = onNavigate)
    }
}

private fun NavController.navigateBottomNav(destination: BottomNavDestination) {
    val route = when (destination) {
        BottomNavDestination.Home -> AppRoutes.Dashboard.route
        BottomNavDestination.History -> AppRoutes.History.route
        BottomNavDestination.NewSession -> AppRoutes.NewSession.route
        BottomNavDestination.Ballistics -> AppRoutes.Ballistics.route
        BottomNavDestination.Settings -> AppRoutes.Settings.route
    }
    navigate(route) {
        if (destination != BottomNavDestination.NewSession) {
            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
            launchSingleTop = true
        }
    }
}