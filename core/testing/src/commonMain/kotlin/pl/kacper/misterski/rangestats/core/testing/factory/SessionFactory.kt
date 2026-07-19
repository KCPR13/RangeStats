package pl.kacper.misterski.rangestats.core.testing.factory

import pl.kacper.misterski.rangestats.core.domain.Constants
import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Shot

fun testSession(
    id: Long = 1L,
    weaponName: String = "test-weapon-1",
    locationName: String = "Test Range",
    distanceMeters: Int = Constants.DEFAULT_DISTANCE_METERS,
    targetType: TargetType = TargetType.BULLSEYE,
    shots: List<Shot> = listOf(testShot(sessionId = id)),
    startedAt: Long = 1_000_000L,
    finishedAt: Long? = 1_003_600L,
    score: Float? = 9.0f,
): Session = Session(
    id = id,
    weaponName = weaponName,
    locationName = locationName,
    distanceMeters = distanceMeters,
    targetType = targetType,
    shots = shots,
    startedAt = startedAt,
    finishedAt = finishedAt,
    score = score,
)

fun testShot(
    id: Long = 1L,
    sessionId: Long = 1L,
    zoneHit: TargetZone = TargetZone.TEN,
    timestamp: Long = 1_001_000L,
): Shot = Shot(
    id = id,
    sessionId = sessionId,
    zoneHit = zoneHit,
    timestamp = timestamp,
)
