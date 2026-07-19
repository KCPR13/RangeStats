package pl.kacper.misterski.rangestats.core.domain.models

import pl.kacper.misterski.rangestats.core.domain.enums.TargetType

data class Session(
    val id: Long,
    val weaponName: String,
    val locationName: String,
    val distanceMeters: Int,
    val targetType: TargetType,
    val shots: List<Shot>,
    val startedAt: Long,
    val finishedAt: Long?,
    val score: Float?,
)
