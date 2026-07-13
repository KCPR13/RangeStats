package pl.kacper.misterski.rangestats.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.rangestats.core.navigation.AppRoutes
import pl.kacper.misterski.rangestats.feature.onboarding.domain.usecase.IsOnboardingCompletedUseCase
import kotlin.time.Duration.Companion.milliseconds

//TODO K separate file
sealed interface AppStartDestination {
    data object Loading : AppStartDestination
    data class Ready(val route: String) : AppStartDestination
}

class AppViewModel(
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase,
) : ViewModel() {

    private val _startDestination =
        MutableStateFlow<AppStartDestination>(AppStartDestination.Loading)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(MIN_LOADING_DELAY_MS.milliseconds)
            val result = isOnboardingCompletedUseCase()
            _startDestination.update {
                result.fold(
                    onSuccess = { completed ->
                        AppStartDestination.Ready(
                            if (completed) AppRoutes.Dashboard.route else AppRoutes.Onboarding.route,
                        )
                    },
                    onFailure = { AppStartDestination.Ready(AppRoutes.Onboarding.route) },
                )
            }
        }
    }

    private companion object {
        const val MIN_LOADING_DELAY_MS = 1000L
    }
}
