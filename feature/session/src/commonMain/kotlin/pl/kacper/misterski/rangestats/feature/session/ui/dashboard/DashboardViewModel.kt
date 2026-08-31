package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.navigation.Navigator

class DashboardViewModel(
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(DashboardUiModel())
    val uiModel: StateFlow<DashboardUiModel> = _uiModel.asStateFlow()

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.OpenHistory -> navigator.navigateTo(AppRoutes.History)
        }
    }
}