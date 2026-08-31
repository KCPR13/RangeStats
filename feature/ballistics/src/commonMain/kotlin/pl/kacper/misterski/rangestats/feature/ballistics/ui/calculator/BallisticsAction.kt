package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import pl.kacper.misterski.rangestats.core.navigation.BottomNavDestination

sealed class BallisticsAction {
    data object OnStart : BallisticsAction()
    data class SelectPreset(val name: String) : BallisticsAction()
    data class SelectBcModel(val index: Int) : BallisticsAction()
    data class UpdateMuzzleVelocity(val value: String) : BallisticsAction()
    data class UpdateBulletMass(val value: String) : BallisticsAction()
    data class UpdateBallisticCoefficient(val value: String) : BallisticsAction()
    data class UpdateZeroRange(val value: String) : BallisticsAction()
    data class UpdateTargetDistance(val value: String) : BallisticsAction()
    data class UpdateScopeHeight(val value: String) : BallisticsAction()
    data object Calculate : BallisticsAction()
    data class OnBottomNavigate(val destination: BottomNavDestination) : BallisticsAction()
}
