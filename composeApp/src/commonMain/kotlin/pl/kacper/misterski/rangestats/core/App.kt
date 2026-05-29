package pl.kacper.misterski.rangestats.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator.ballistics
import pl.kacper.misterski.rangestats.feature.history.ui.detail.sessionDetail
import pl.kacper.misterski.rangestats.feature.history.ui.list.history
import pl.kacper.misterski.rangestats.feature.onboarding.ui.onboarding
import pl.kacper.misterski.rangestats.feature.session.ui.active.activeSession
import pl.kacper.misterski.rangestats.feature.session.ui.dashboard.dashboard
import pl.kacper.misterski.rangestats.feature.session.ui.new.newSession
import pl.kacper.misterski.rangestats.feature.session.ui.summary.sessionSummary
import pl.kacper.misterski.rangestats.feature.settings.ui.settings.settings
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.weaponList
import pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add.addWeapon

@Composable
fun App() {
    RangeStatsTheme {
        val viewModel = koinViewModel<AppViewModel>()
        val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
        val navController = rememberNavController()
        val route = (startDestination as? AppStartDestination.Ready)?.route ?: return@RangeStatsTheme

        //TODO CLEANUP
        NavHost(
            navController = navController,
            startDestination = route,
        ) {
            onboarding(
                onComplete = {
                    navController.navigate(AppRoutes.Dashboard.route) {
                        popUpTo(AppRoutes.Onboarding.route) { inclusive = true }
                    }
                },
            )

            dashboard(
                onNewSession = {
                    navController.navigate(AppRoutes.NewSession.route)
                },
                onOpenHistory = {
                    navController.navigate(AppRoutes.History.route)
                },
                onNavigate = { destination -> navController.navigateBottomNav(destination) },
            )

            newSession(
                onSessionStarted = { sessionId ->
                    navController.navigate(AppRoutes.ActiveSession(sessionId).createRoute()) {
                        popUpTo(AppRoutes.NewSession.route) { inclusive = true }
                    }
                },
                onBack = { navController.navigateUp() },
            )

            activeSession(
                onSessionFinished = { sessionId ->
                    navController.navigate(AppRoutes.SessionSummary(sessionId).createRoute()) {
                        popUpTo(AppRoutes.ActiveSession.ROUTE) { inclusive = true }
                    }
                },
                onBack = { navController.navigateUp() },
            )

            sessionSummary(
                onSaved = {
                    navController.navigate(AppRoutes.Dashboard.route) {
                        popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBack = { navController.navigateUp() },
            )

            history(
                onOpenDetail = { sessionId ->
                    navController.navigate(AppRoutes.SessionDetail(sessionId).createRoute())
                },
                onNavigate = { destination -> navController.navigateBottomNav(destination) },
            )

            sessionDetail(
                onBack = { navController.navigateUp() },
            )

            ballistics(
                onNavigate = { destination -> navController.navigateBottomNav(destination) },
            )

            settings(
                onNavigateToWeaponList = {
                    navController.navigate(AppRoutes.WeaponList.route)
                },
            )

            weaponList(
                onBack = { navController.navigateUp() },
                showAddWeapon = {
                    navController.navigate(AppRoutes.AddWeapon.route)
                },
            )

            addWeapon(
                onBack = { navController.navigateUp() },
            )
        }
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
