package pl.kacper.misterski.rangestats.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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

@Composable
fun App() {
    RangeStatsTheme {
        val navController = rememberNavController()

        //TODO CLEANUP
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Onboarding.route,
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
                onNavigate = { destination ->
                    when (destination) {
                        BottomNavDestination.Home -> navController.navigate(AppRoutes.Dashboard.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        BottomNavDestination.NewSession -> navController.navigate(AppRoutes.NewSession.route)
                        BottomNavDestination.History -> navController.navigate(AppRoutes.History.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        BottomNavDestination.Ballistics -> navController.navigate(AppRoutes.Ballistics.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        else -> {}
                    }
                },
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
                        popUpTo(AppRoutes.ActiveSession("").route) { inclusive = true }
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
                onNavigate = { destination ->
                    when (destination) {
                        BottomNavDestination.Home -> navController.navigate(AppRoutes.Dashboard.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        BottomNavDestination.NewSession -> navController.navigate(AppRoutes.NewSession.route)
                        BottomNavDestination.History -> navController.navigate(AppRoutes.History.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        BottomNavDestination.Ballistics -> navController.navigate(AppRoutes.Ballistics.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        else -> {}
                    }
                },
            )

            ballistics(
                onNavigate = { destination ->
                    when (destination) {
                        BottomNavDestination.Home -> navController.navigate(AppRoutes.Dashboard.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        BottomNavDestination.NewSession -> navController.navigate(AppRoutes.NewSession.route)
                        BottomNavDestination.History -> navController.navigate(AppRoutes.History.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        BottomNavDestination.Ballistics -> navController.navigate(AppRoutes.Ballistics.route) {
                            popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        else -> {}
                    }
                },
            )

            sessionDetail(
                onBack = { navController.navigateUp() },
            )
        }
    }
}
