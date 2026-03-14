package pl.kacper.misterski.rangestats.core.domain.models

import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem

data class UserProfile(
    val id: String,
    val displayName: String,
    val units: UnitSystem,
    val defaultDistanceMeters: Int,
)
