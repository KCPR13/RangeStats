package pl.kacper.misterski.rangestats.core.navigation

sealed interface NavigatorCommand {
    data class NavigateTo(
        val route: AppRoutes,
        val options: NavOptions,
    ) : NavigatorCommand

    data object Back : NavigatorCommand
}
