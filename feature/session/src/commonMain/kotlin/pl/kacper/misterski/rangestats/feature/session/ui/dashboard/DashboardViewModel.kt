package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class DashboardViewModel : ViewModel() {

    private val _uiModel = MutableStateFlow(DashboardUiModel())
    val uiModel: StateFlow<DashboardUiModel> = _uiModel.asStateFlow()

    init {
        load() // TODO onstart
    }

    fun onAction(action: DashboardAction) { // TODO
        // Navigation actions are handled in DashboardNavigation
    }

    private fun load() {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = false) }
        }
    }
}
