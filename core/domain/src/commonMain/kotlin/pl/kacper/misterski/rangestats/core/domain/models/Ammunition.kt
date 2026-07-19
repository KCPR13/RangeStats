package pl.kacper.misterski.rangestats.core.domain.models

import pl.kacper.misterski.rangestats.core.domain.enums.Caliber
import pl.kacper.misterski.rangestats.core.domain.enums.ShotgunGauge
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType

sealed class Ammunition {
    data class CaliberAmmo(val caliber: Caliber) : Ammunition()
    data class GaugeAmmo(val gauge: ShotgunGauge) : Ammunition()

    val displayLabel: String
        get() = when (this) {
            is CaliberAmmo -> caliber.label
            is GaugeAmmo -> gauge.label
        }

    fun isApplicableTo(type: WeaponType): Boolean = when (this) {
        is CaliberAmmo -> type in caliber.applicableTo
        is GaugeAmmo -> type == WeaponType.SHOTGUN
    }
}