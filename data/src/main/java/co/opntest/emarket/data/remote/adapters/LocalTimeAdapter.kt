package co.opntest.emarket.data.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.DateTimeException
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object LocalTimeAdapter {
    /**
     * Parses an ISO_OFFSET_TIME string and adjusts it to the device's current offset.
     */
    @FromJson
    fun fromJson(value: String?): LocalTime? {
        return value?.toLocalTime()
    }

    /**
     * Formats a LocalTime into an ISO_OFFSET_TIME string using the device's current offset.
     */
    @ToJson
    fun toJson(value: LocalTime): String {
        val deviceOffset = ZonedDateTime.now().offset
        return value.atOffset(deviceOffset).format(DateTimeFormatter.ISO_OFFSET_TIME)
    }
}


/**
 * Parses an ISO_OFFSET_TIME string into a LocalTime adjusted to the device's current offset.
 */
private fun String.toLocalTime(): LocalTime? {
    return try {
        val parsed = OffsetTime.parse(this, DateTimeFormatter.ISO_OFFSET_TIME)
        val deviceOffset = ZonedDateTime.now().offset
        parsed.withOffsetSameInstant(deviceOffset).toLocalTime()
    } catch (_: DateTimeException) {
        null
    }
}
