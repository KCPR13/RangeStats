package pl.kacper.misterski.rangestats.feature.session.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.feature.session.ui.active.activeSession
import pl.kacper.misterski.rangestats.feature.session.ui.new.newSession
import pl.kacper.misterski.rangestats.feature.session.ui.summary.sessionSummary

fun NavGraphBuilder.sessionFlow(navController: NavHostController, onBack: () -> Unit) {
    newSession(
        onSessionStarted = { sessionId ->
            navController.navigate(AppRoutes.ActiveSession(sessionId).createRoute()) {
                popUpTo(AppRoutes.NewSession.route) { inclusive = true }
            }
        },
        onBack = onBack,
    )

    activeSession(
        onSessionFinished = { sessionId ->
            navController.navigate(AppRoutes.SessionSummary(sessionId).createRoute()) {
                popUpTo(AppRoutes.ActiveSession.ROUTE) { inclusive = true }
            }
        },
        onBack = onBack,
    )

    sessionSummary(
        onSaved = {
            navController.navigate(AppRoutes.Dashboard.route) {
                popUpTo(AppRoutes.Dashboard.route) { inclusive = false }
                launchSingleTop = true
            }
        },
        onBack = onBack,
    )
}