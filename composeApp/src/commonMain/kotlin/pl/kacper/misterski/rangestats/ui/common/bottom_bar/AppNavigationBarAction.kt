package pl.kacper.misterski.rangestats.ui.common.bottom_bar

sealed interface AppNavigationBarAction{
    data object Train: AppNavigationBarAction
    data object Stats: AppNavigationBarAction
    data object Settings: AppNavigationBarAction
    data object About: AppNavigationBarAction
}
