package app.core.utils.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toLongTime(): String {
    return DateTimeFormatter.ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochSecond(this))
}

fun Long.toShortTime(): String {
    return DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochSecond(this))
}
