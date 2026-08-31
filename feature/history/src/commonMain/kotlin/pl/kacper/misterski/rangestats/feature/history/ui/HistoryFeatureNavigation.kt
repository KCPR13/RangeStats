package pl.kacper.misterski.rangestats.feature.history.ui

import androidx.navigation.NavGraphBuilder
import pl.kacper.misterski.rangestats.feature.history.ui.detail.sessionDetail
import pl.kacper.misterski.rangestats.feature.history.ui.list.history

fun NavGraphBuilder.historyFlow() {
    history()
    sessionDetail()
}
