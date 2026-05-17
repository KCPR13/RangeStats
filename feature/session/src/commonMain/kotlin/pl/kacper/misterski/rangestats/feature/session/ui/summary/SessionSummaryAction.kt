package pl.kacper.misterski.rangestats.feature.session.ui.summary

sealed class SessionSummaryAction {
    data object Save : SessionSummaryAction()
    data object Share : SessionSummaryAction()
    data object Back : SessionSummaryAction()
}
