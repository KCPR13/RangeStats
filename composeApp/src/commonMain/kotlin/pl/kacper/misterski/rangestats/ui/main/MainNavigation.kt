package pl.kacper.misterski.rangestats.ui.main

import androidx.navigation.NavGraphBuilder
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.AppRoutes
import pl.kacper.misterski.rangestats.ui.common.utils.animatedDestination

fun NavGraphBuilder.main(showBottomSheet: () -> Unit){
    animatedDestination(AppRoutes.Main) {
        val viewModel = koinViewModel<MainViewModel>()

        MainScreen(onAction = {
            when(it){
                MainAction.ShowBottomSheet -> showBottomSheet()
            }
        })
    }
}