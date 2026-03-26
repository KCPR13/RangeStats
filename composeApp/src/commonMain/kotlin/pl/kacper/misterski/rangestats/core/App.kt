package pl.kacper.misterski.rangestats.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.feature.onboarding.ui.onboarding

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

            composable(route = AppRoutes.Dashboard.route) {
                HomeScreen()
            }
        }
    }
}

@Composable
private fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TacBgDeep)
            .padding(Dimen.dp24),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "RangeStats",
            color = TacTextSecondary,
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    RangeStatsTheme {
        HomeScreen()
    }
}
