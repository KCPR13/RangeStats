package pl.kacper.misterski.rangestats.ui.login

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import org.koin.compose.viewmodel.koinViewModel
import pl.kacper.misterski.rangestats.core.AppRoutes
import pl.kacper.misterski.rangestats.core.getName
import pl.kacper.misterski.rangestats.ui.common.bottom_sheet.bottomSheet

fun NavGraphBuilder.login(onDismiss: () -> Unit) {
    bottomSheet(AppRoutes.Login.getName()) {
        val viewModel = koinViewModel<LoginViewModel>()
        val uiModel by viewModel.uiModel.collectAsStateWithLifecycle()

        LoginScreen(
            model = uiModel,
            onAction = viewModel::onAction,
            onDismiss = onDismiss
        )
    }
}