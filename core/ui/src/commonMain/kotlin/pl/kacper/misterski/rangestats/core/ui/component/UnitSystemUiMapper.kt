package pl.kacper.misterski.rangestats.core.ui.component

import org.jetbrains.compose.resources.StringResource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.ui.Res
import pl.kacper.misterski.rangestats.core.ui.core_unit_suffix_meters
import pl.kacper.misterski.rangestats.core.ui.core_unit_suffix_yards

fun UnitSystem.toDistanceUnitSuffixRes(): StringResource = when (this) {
    UnitSystem.METRIC -> Res.string.core_unit_suffix_meters
    UnitSystem.IMPERIAL -> Res.string.core_unit_suffix_yards
}
