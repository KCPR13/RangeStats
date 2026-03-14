package pl.kacper.misterski.rangestats.core.domain.models

import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone

data class Shot(
    val id: String,
    val sessionId: String,
    val zoneHit: TargetZone,
    val timestamp: Long,
)
