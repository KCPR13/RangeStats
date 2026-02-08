package pl.kacper.misterski.rangestats.core

sealed class AppRoutes {
    data object Main: AppRoutes()
    data object Login: AppRoutes()
}

fun AppRoutes.getName(): String{
    return this::class.simpleName!!
}