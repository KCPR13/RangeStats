package pl.kacper.misterski.rangestats.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.IsOnboardingCompletedUseCase

sealed interface AppStartDestination {
    data object Loading : AppStartDestination
    data class Ready(val route: String) : AppStartDestination
}

class AppViewModel(
    private val isOnboardingCompleted: IsOnboardingCompletedUseCase,
) : ViewModel() {

    private val _startDestination = MutableStateFlow<AppStartDestination>(AppStartDestination.Loading)
    val startDestination = _startDestination.asStateFlow()

    //TODO weird
    init {
        viewModelScope.launch {
            _startDestination.value = try {
                if (isOnboardingCompleted()) {
                    AppStartDestination.Ready(AppRoutes.Dashboard.route)
                } else {
                    AppStartDestination.Ready(AppRoutes.Onboarding.route)
                }
            } catch (e: Exception) {
                AppStartDestination.Ready(AppRoutes.Onboarding.route)
            }
        }
    }
}
