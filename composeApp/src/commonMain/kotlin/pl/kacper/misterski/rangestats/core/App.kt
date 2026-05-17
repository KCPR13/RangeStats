package pl.kacper.misterski.rangestats.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.feature.onboarding.ui.onboarding
import pl.kacper.misterski.rangestats.feature.session.ui.active.activeSession
import pl.kacper.misterski.rangestats.feature.session.ui.dashboard.dashboard

@Composable
fun App() {
    RangeStatsTheme {
        val navController = rememberNavController()

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

            //TODO lambdas
            dashboard(
                onOpenHistory = {},
                onNewSession = {},
                onNavigate = {},
            )

            activeSession(
                onSessionFinished = {},
                onBack = {}
            )

        }
    }
}
