package pl.kacper.misterski.rangestats.feature.history.ui

import androidx.navigation.NavGraphBuilder
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.feature.history.ui.detail.sessionDetail
import pl.kacper.misterski.rangestats.feature.history.ui.list.history

fun NavGraphBuilder.historyFlow(onBottomNavigate: (BottomNavDestination) -> Unit) {
    history(onBottomNavigate = onBottomNavigate)
    sessionDetail()
}