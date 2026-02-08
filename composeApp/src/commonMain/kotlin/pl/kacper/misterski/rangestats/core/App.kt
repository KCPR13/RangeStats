package pl.kacper.misterski.rangestats.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.rangestats.ui.login.login
import pl.kacper.misterski.rangestats.ui.main.main

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppRoutes.Main.getName(),
        ) {
            main(showBottomSheet = {
                navController.navigate(AppRoutes.Login.getName())
            })
            login(
                onDismiss = navController::navigateUp
            )
        }
    }
}