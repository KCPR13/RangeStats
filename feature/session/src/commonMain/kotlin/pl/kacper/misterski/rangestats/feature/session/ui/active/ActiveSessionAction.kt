package pl.kacper.misterski.rangestats.feature.session.ui.active

sealed class ActiveSessionAction {
    data class AnalyzeTarget(val imageBytes: ByteArray) : ActiveSessionAction()
    data object FinishSession : ActiveSessionAction()
    data object Back : ActiveSessionAction()
    data class OnStart(val sessionId: Long) : ActiveSessionAction()
}
