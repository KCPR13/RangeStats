package pl.kacper.misterski.rangestats.core.data.mapper

import pl.kacper.misterski.rangestats.core.data.database.session.SessionEntity
import pl.kacper.misterski.rangestats.core.data.database.shot.ShotEntity
import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Shot

fun SessionEntity.toDomain(shots: List<ShotEntity>): Session = Session(
    id = id,
    weaponName = weaponName,
    locationName = locationName,
    distanceMeters = distanceMeters,
    targetType = TargetType.entries.find { it.name == targetType } ?: TargetType.ISSF_ROUND,
    shots = shots.map { it.toDomain() },
    startedAt = startedAt,
    finishedAt = finishedAt,
    score = score,
)

fun Session.toEntity(): SessionEntity = SessionEntity(
    id = id,
    weaponName = weaponName,
    locationName = locationName,
    distanceMeters = distanceMeters,
    targetType = targetType.name,
    startedAt = startedAt,
    finishedAt = finishedAt,
    score = score,
)

fun ShotEntity.toDomain(): Shot = Shot(
    id = id,
    sessionId = sessionId,
    zoneHit = TargetZone.entries.find { it.name == zoneHit } ?: TargetZone.MISS,
    timestamp = timestamp,
)

fun Shot.toEntity(): ShotEntity = ShotEntity(
    id = id,
    sessionId = sessionId,
    zoneHit = zoneHit.name,
    timestamp = timestamp,
)
