package pl.kacper.misterski.rangestats.feature.history.ui.detail

sealed class SessionDetailAction {
    data class Load(val sessionId: String) : SessionDetailAction()
    data object Delete : SessionDetailAction()
    data object Back : SessionDetailAction()
    data object NavigationHandled : SessionDetailAction()
}
