package pl.kacper.misterski.rangestats.feature.session.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.domain.usecase.GetWeaponByNameUseCase
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.core.navigation.NavOptions
import pl.kacper.misterski.rangestats.core.navigation.Navigator
import pl.kacper.misterski.rangestats.feature.session.domain.usecase.GetSessionUseCase

class SessionSummaryViewModel(
    private val getSessionUseCase: GetSessionUseCase,
    private val getWeaponByNameUseCase: GetWeaponByNameUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(SessionSummaryUiModel())
    val uiModel: StateFlow<SessionSummaryUiModel> = _uiModel.asStateFlow()

    fun onAction(action: SessionSummaryAction) {
        when (action) {
            is SessionSummaryAction.Load -> load(action.sessionId)
            SessionSummaryAction.Save -> navigator.navigateTo(
                AppRoutes.Dashboard,
                NavOptions(popUpTo = AppRoutes.Dashboard, popUpToInclusive = false, launchSingleTop = true),
            )
            SessionSummaryAction.Share -> Unit
            SessionSummaryAction.Back -> navigator.back()
        }
    }

    private fun load(sessionId: Long) {
        viewModelScope.launch {
            _uiModel.update { it.copy(isLoading = true, sessionId = sessionId) }
            getSessionUseCase(sessionId)
                .onSuccess { session ->
                    val weapon = getWeaponByNameUseCase(session.weaponName).firstOrNull()
                    _uiModel.update { session.toUiModel(weapon) }
                }
                .onFailure { _uiModel.update { it.copy(isLoading = false) } }
        }
    }
}
