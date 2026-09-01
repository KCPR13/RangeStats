package pl.kacper.misterski.rangestats.feature.ballistics.data.repository

import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import pl.kacper.misterski.rangestats.feature.ballistics.domain.repository.BallisticsRepository

class BallisticsRepositoryImpl : BallisticsRepository {

    override fun getCaliberPresets(): List<CaliberPreset> = listOf(
        CaliberPreset(
            caliber = Caliber.REMINGTON_223,
            displayLabel = ".223 Remington",
            muzzleVelocityMs = 945.0,
            bulletMassGrains = 55.0,
            ballisticCoefficient = 0.240,
        ),
        CaliberPreset(
            caliber = Caliber.NATO_5_56,
            displayLabel = "5.56x45 NATO",
            muzzleVelocityMs = 925.0,
            bulletMassGrains = 55.0,
            ballisticCoefficient = 0.243,
        ),
        CaliberPreset(
            caliber = Caliber.CREEDMOOR_6_5,
            displayLabel = "6.5 Creedmoor",
            muzzleVelocityMs = 823.0,
            bulletMassGrains = 140.0,
            ballisticCoefficient = 0.610,
        ),
        CaliberPreset(
            caliber = Caliber.WIN_308,
            displayLabel = ".308 Win",
            muzzleVelocityMs = 800.0,
            bulletMassGrains = 175.0,
            ballisticCoefficient = 0.475,
        ),
        CaliberPreset(
            caliber = Caliber.WIN_300_MAGNUM,
            displayLabel = ".300 Win Mag",
            muzzleVelocityMs = 880.0,
            bulletMassGrains = 190.0,
            ballisticCoefficient = 0.550,
        ),
        CaliberPreset(
            caliber = Caliber.SOVIET_7_62X39,
            displayLabel = "7.62x39",
            muzzleVelocityMs = 715.0,
            bulletMassGrains = 123.0,
            ballisticCoefficient = 0.295,
        ),
        CaliberPreset(
            caliber = Caliber.LAPUA_338,
            displayLabel = ".338 Lapua",
            muzzleVelocityMs = 905.0,
            bulletMassGrains = 250.0,
            ballisticCoefficient = 0.675,
        ),
    )
}
