package pl.kacper.misterski.rangestats.feature.ballistics.data.repository

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import pl.kacper.misterski.rangestats.feature.ballistics.domain.repository.BallisticsRepository

class BallisticsRepositoryImpl : BallisticsRepository {

    //TODO hardcoded
    override fun getCaliberPresets(): List<CaliberPreset> = listOf(
        CaliberPreset(name = "9mm Para",    muzzleVelocityMs = 370.0,  bulletMassGrains = 115.0, ballisticCoefficient = 0.131),
        CaliberPreset(name = ".45 ACP",     muzzleVelocityMs = 259.0,  bulletMassGrains = 230.0, ballisticCoefficient = 0.195),
        CaliberPreset(name = "5.56 NATO",   muzzleVelocityMs = 925.0,  bulletMassGrains = 55.0,  ballisticCoefficient = 0.243),
        CaliberPreset(name = "7.62x39",     muzzleVelocityMs = 715.0,  bulletMassGrains = 123.0, ballisticCoefficient = 0.295),
        CaliberPreset(name = ".308 Win",    muzzleVelocityMs = 800.0,  bulletMassGrains = 175.0, ballisticCoefficient = 0.475),
        CaliberPreset(name = ".357 Magnum", muzzleVelocityMs = 425.0,  bulletMassGrains = 158.0, ballisticCoefficient = 0.180),
        CaliberPreset(name = "12ga Slug",   muzzleVelocityMs = 457.0,  bulletMassGrains = 437.0, ballisticCoefficient = 0.110),
    )
}
