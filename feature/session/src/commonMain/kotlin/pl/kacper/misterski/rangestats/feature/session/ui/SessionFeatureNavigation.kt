package pl.kacper.misterski.rangestats.feature.session.ui

import androidx.navigation.NavGraphBuilder
import pl.kacper.misterski.rangestats.feature.session.ui.active.activeSession
import pl.kacper.misterski.rangestats.feature.session.ui.new.newSession
import pl.kacper.misterski.rangestats.feature.session.ui.summary.sessionSummary

fun NavGraphBuilder.sessionFlow() {
    newSession()
    activeSession()
    sessionSummary()
}
