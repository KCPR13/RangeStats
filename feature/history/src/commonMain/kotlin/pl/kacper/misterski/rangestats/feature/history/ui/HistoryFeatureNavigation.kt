package pl.kacper.misterski.rangestats.feature.history.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.feature.history.ui.detail.sessionDetail
import pl.kacper.misterski.rangestats.feature.history.ui.list.history

fun NavGraphBuilder.historyFlow(
    navController: NavHostController,
    onBack: () -> Unit,
    onNavigate: (BottomNavDestination) -> Unit,
) {
    history(
        onOpenDetail = { sessionId ->
            navController.navigate(AppRoutes.SessionDetail(sessionId).createRoute())
        },
        onNavigate = onNavigate,
    )

    sessionDetail(onBack = onBack)
}