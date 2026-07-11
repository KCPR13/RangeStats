package pl.kacper.misterski.rangestats.feature.ballistics.domain.exceptions

sealed class InvalidBallisticsInputException(message: String) : IllegalArgumentException(message) {
    data object InvalidMuzzleVelocity : InvalidBallisticsInputException("Muzzle velocity must be positive")
    data object InvalidBallisticCoefficient : InvalidBallisticsInputException("Ballistic coefficient must be positive")
    data object InvalidBulletMass : InvalidBallisticsInputException("Bullet mass must be positive")
    data object InvalidZeroRange : InvalidBallisticsInputException("Zero range must be positive")
    data object InvalidTargetDistance : InvalidBallisticsInputException("Target distance must be positive")
}
