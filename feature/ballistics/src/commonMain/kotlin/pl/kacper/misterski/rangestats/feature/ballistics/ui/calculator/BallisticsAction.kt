package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

sealed class BallisticsAction {
    data class SelectPreset(val index: Int) : BallisticsAction()
    data class UpdateMuzzleVelocity(val value: String) : BallisticsAction()
    data class UpdateBulletMass(val value: String) : BallisticsAction()
    data class UpdateBallisticCoefficient(val value: String) : BallisticsAction()
    data class UpdateZeroRange(val value: String) : BallisticsAction()
    data class UpdateTargetDistance(val value: String) : BallisticsAction()
    data class UpdateScopeHeight(val value: String) : BallisticsAction()
    data object Calculate : BallisticsAction()
}
