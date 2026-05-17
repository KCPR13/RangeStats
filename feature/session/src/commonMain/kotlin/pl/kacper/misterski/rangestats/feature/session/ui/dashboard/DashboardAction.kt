package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

sealed class DashboardAction {
    data object NewSession : DashboardAction()
    data object OpenHistory : DashboardAction()
    data object OpenSettings : DashboardAction()
}
