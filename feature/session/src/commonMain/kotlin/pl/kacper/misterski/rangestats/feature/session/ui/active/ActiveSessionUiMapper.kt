package pl.kacper.misterski.rangestats.feature.session.ui.active

import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.feature.session.currentTimeMillis

private const val TIME_FORMAT_HMS = "%02d:%02d:%02d"
private const val TIME_FORMAT_MS = "%02d:%02d"
private const val MILLIS_PER_SECOND = 1000
private const val SECONDS_PER_HOUR = 3600
private const val SECONDS_PER_MINUTE = 60

fun Session.toUiModel(weapon: Weapon?): ActiveSessionUiModel {
    val elapsedSeconds = ((finishedAt ?: currentTimeMillis()) - startedAt) / MILLIS_PER_SECOND
    val h = elapsedSeconds / SECONDS_PER_HOUR
    val m = (elapsedSeconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE
    val s = elapsedSeconds % SECONDS_PER_MINUTE
    val elapsedTimeFormatted = if (h > 0) {
        TIME_FORMAT_HMS.format(h, m, s)
    } else {
        TIME_FORMAT_MS.format(m, s)
    }

    return ActiveSessionUiModel(
        sessionId = id,
        locationName = locationName,
        ammoLabel = weapon?.ammunition?.displayLabel.orEmpty(),
        distanceMeters = distanceMeters,
        elapsedTimeFormatted = elapsedTimeFormatted
    )
}