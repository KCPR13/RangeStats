package pl.kacper.misterski.rangestats.core.domain.converter

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitConverterTest {

    private val converter = UnitConverter()

    @Test
    fun `metric toDisplay returns meters unchanged`() {
        assertEquals(5, converter.toDisplay(5, UnitSystem.METRIC))
        assertEquals(100, converter.toDisplay(100, UnitSystem.METRIC))
        assertEquals(300, converter.toDisplay(300, UnitSystem.METRIC))
    }

    @Test
    fun `imperial toDisplay converts meters to yards with rounding`() {
        assertEquals(5, converter.toDisplay(5, UnitSystem.IMPERIAL))
        assertEquals(109, converter.toDisplay(100, UnitSystem.IMPERIAL))
        assertEquals(328, converter.toDisplay(300, UnitSystem.IMPERIAL))
    }

    @Test
    fun `metric toMeters returns display value unchanged`() {
        assertEquals(5, converter.toMeters(5, UnitSystem.METRIC))
        assertEquals(300, converter.toMeters(300, UnitSystem.METRIC))
    }

    @Test
    fun `imperial toMeters converts yards to meters with rounding`() {
        assertEquals(5, converter.toMeters(5, UnitSystem.IMPERIAL))
        assertEquals(91, converter.toMeters(100, UnitSystem.IMPERIAL))
        assertEquals(300, converter.toMeters(328, UnitSystem.IMPERIAL))
    }
}