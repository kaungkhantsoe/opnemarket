package co.opntest.emarket.extensions

import java.time.DateTimeException
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

const val DATE_TIME_PATTERN_H_A = "h a"
const val DATE_TIME_PATTERN_H_MM_A = "h:mm a"

/**
 * Define two patterns:
 * - "h a" if minutes == 0  (e.g. "9 AM")
 * - "h:mm a" otherwise (e.g. "9:30 AM")
 */
fun LocalTime.formatTo12HourString(locale: Locale = Locale.getDefault()): String {
    val pattern = when (minute) {
        0 -> DATE_TIME_PATTERN_H_A
        else -> DATE_TIME_PATTERN_H_MM_A
    }
    return this.format(pattern, locale)
}

fun LocalTime.format(
    pattern: String,
    locale: Locale = Locale.getDefault(),
): String = try {
    this.format(DateTimeFormatter.ofPattern(pattern, locale))
} catch (_: DateTimeException) {
    ""
}
