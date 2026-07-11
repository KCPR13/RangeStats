package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

sealed class DashboardAction {
    data object OpenHistory : DashboardAction()
}
