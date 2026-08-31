package pl.kacper.misterski.rangestats.core.navigation

data class NavOptions(
    val popUpTo: AppRoutes? = null,
    val popUpToInclusive: Boolean = false,
    val launchSingleTop: Boolean = false,
)
