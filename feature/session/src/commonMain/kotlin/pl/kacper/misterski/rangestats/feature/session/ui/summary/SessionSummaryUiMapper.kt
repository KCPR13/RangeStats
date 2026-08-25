package pl.kacper.misterski.rangestats.feature.session.ui.summary

import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Weapon

//TODO should be here?
private val ZONE_ORDER = listOf(
    TargetZone.X, TargetZone.TEN, TargetZone.NINE, TargetZone.EIGHT,
    TargetZone.SEVEN, TargetZone.SIX, TargetZone.MISS,
)

private const val MILLIS_PER_MINUTE = 60000

fun Session.toUiModel(weapon: Weapon?): SessionSummaryUiModel {
    val zones = mutableMapOf<TargetZone, Int>()
    shots.forEach { shot ->
        zones[shot.zoneHit] = (zones[shot.zoneHit] ?: 0) + 1
    }
    val misses = zones[TargetZone.MISS] ?: 0
    val hits = shots.size - misses
    val durationMin = finishedAt?.let { ((it - startedAt) / MILLIS_PER_MINUTE).toInt() } ?: 0
    val maxZoneCount = zones.values.maxOrNull()?.coerceAtLeast(1) ?: 1
    val zoneRows = ZONE_ORDER.map { zone ->
        val count = zones[zone] ?: 0
        val label = when (zone) {
            TargetZone.X -> "X"
            TargetZone.TEN -> "10"
            TargetZone.NINE -> "9"
            TargetZone.EIGHT -> "8"
            TargetZone.SEVEN -> "7"
            TargetZone.SIX -> "6"
            TargetZone.MISS -> "✕"
        }
        SessionSummaryUiModel.ZoneRowUiModel(
            label = label,
            count = count,
            fraction = count.toFloat() / maxZoneCount,
            isMiss = zone == TargetZone.MISS,
        )
    }

    return SessionSummaryUiModel(
        sessionId = id,
        locationName = locationName,
        ammoLabel = weapon?.ammunition?.displayLabel.orEmpty(),
        distanceMeters = distanceMeters,
        durationMinutes = durationMin,
        scoreLabel = score?.let { "${it.toInt()}%" } ?: "—",
        totalHits = hits,
        totalMisses = misses,
        targetCount = shots.size,
        zoneRows = zoneRows,
    )
}