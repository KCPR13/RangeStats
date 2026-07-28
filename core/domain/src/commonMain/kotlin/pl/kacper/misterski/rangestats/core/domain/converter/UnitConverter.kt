package pl.kacper.misterski.rangestats.core.domain.converter

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import kotlin.math.roundToInt

class UnitConverter {
    fun toDisplay(meters: Int, units: UnitSystem): Int = when (units) {
        UnitSystem.METRIC -> meters
        UnitSystem.IMPERIAL -> (meters / METERS_PER_YARD).roundToInt()
    }

    fun toMeters(displayValue: Int, units: UnitSystem): Int = when (units) {
        UnitSystem.METRIC -> displayValue
        UnitSystem.IMPERIAL -> (displayValue * METERS_PER_YARD).roundToInt()
    }

    companion object {
        private const val METERS_PER_YARD = 0.9144
    }
}
