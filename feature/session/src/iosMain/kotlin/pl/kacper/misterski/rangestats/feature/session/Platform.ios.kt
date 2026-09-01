package pl.kacper.misterski.rangestats.feature.session

actual fun platform() = "iOS"

private const val MILLIS_PER_SECOND = 1000

actual fun currentTimeMillis(): Long =
    (platform.Foundation.NSDate.date.timeIntervalSince1970 * MILLIS_PER_SECOND).toLong()
