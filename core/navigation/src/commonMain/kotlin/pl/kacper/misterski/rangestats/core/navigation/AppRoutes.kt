package pl.kacper.misterski.rangestats.core.navigation

sealed class AppRoutes(
    val route: String,
) {
    open fun createRoute(): String = route

    data object Onboarding : AppRoutes("onboarding")

    data object Dashboard : AppRoutes("dashboard")

    data object NewSession : AppRoutes("new_session")

    data object History : AppRoutes("history")

    data object Ballistics : AppRoutes("ballistics")

    data object Settings : AppRoutes("settings")

    data object AddWeapon : AppRoutes("add_weapon")

    data class ActiveSession(
        val sessionId: Long,
    ) : AppRoutes("active_session/{sessionId}") {
        override fun createRoute() = "active_session/$sessionId"

        companion object {
            const val ARG = "sessionId"
            const val ROUTE = "active_session/{sessionId}"
        }
    }

    data class SessionSummary(
        val sessionId: Long,
    ) : AppRoutes("session_summary/{sessionId}") {
        override fun createRoute() = "session_summary/$sessionId"

        companion object {
            const val ARG = "sessionId"
            const val ROUTE = "session_summary/{sessionId}"
        }
    }

    data class SessionDetail(
        val sessionId: Long,
    ) : AppRoutes("session_detail/{sessionId}") {
        override fun createRoute() = "session_detail/$sessionId"

        companion object {
            const val ARG = "sessionId"
            const val ROUTE = "session_detail/{sessionId}"
        }
    }
}
