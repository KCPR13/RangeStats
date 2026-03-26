package pl.kacper.misterski.rangestats.core.navigation

sealed class AppRoutes(val route: String) {
    data object Onboarding : AppRoutes("onboarding")
    data object Dashboard : AppRoutes("dashboard")
    data object NewSession : AppRoutes("new_session")
    data object ActiveSession : AppRoutes("active_session")
    data object SessionSummary : AppRoutes("session_summary")
    data object History : AppRoutes("history")
    data object Ballistics : AppRoutes("ballistics")
    data object Settings : AppRoutes("settings")
    data object WeaponList : AppRoutes("weapon_list")
    data class SessionDetail(val sessionId: String) : AppRoutes("session_detail/{sessionId}") {
        fun createRoute() = "session_detail/$sessionId"
        companion object { const val ARG = "sessionId" }
    }
}