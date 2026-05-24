package pl.kacper.misterski.rangestats.feature.history.ui.list

sealed class HistoryAction {
    data class OpenDetail(val sessionId: String) : HistoryAction()
    data object NavigationHandled : HistoryAction()
}
