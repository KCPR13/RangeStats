package pl.kacper.misterski.rangestats.feature.session

actual fun platform() = "iOS"

actual fun currentTimeMillis(): Long =
    (platform.Foundation.NSDate.date.timeIntervalSince1970 * 1000).toLong()