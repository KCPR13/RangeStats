package pl.kacper.misterski.rangestats.feature.history.ui.list

import pl.kacper.misterski.rangestats.core.navigation.BottomNavDestination

sealed class HistoryAction {
    data class OpenDetail(val sessionId: Long) : HistoryAction()
    data class OnBottomNavigate(val destination: BottomNavDestination) : HistoryAction()
}
