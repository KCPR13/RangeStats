package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel() {

    private val _uiModel = MutableStateFlow(DashboardUiModel())
    val uiModel: StateFlow<DashboardUiModel> = _uiModel.asStateFlow()
}
