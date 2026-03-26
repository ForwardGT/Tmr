package app.core.utils.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String?.toShortTimeFromIso(): String {
    if (this.isNullOrBlank()) return ""

    val pattern = DateTimeFormatter.ofPattern("HH:mm")

    return runCatching {
        pattern.withZone(ZoneId.systemDefault()).format(Instant.parse(this))
    }.recoverCatching {
        LocalDateTime.parse(this).format(pattern)
    }.getOrDefault("")
}
