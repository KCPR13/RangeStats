package pl.kacper.misterski.rangestats.core

sealed interface AppStartDestination {
    data object Loading : AppStartDestination
    data class Ready(val route: String) : AppStartDestination
}