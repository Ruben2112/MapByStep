package com.heveamobile.mapbystep

import java.sql.Date
import java.text.DateFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.time.Instant
import kotlin.time.toJavaInstant

actual fun formatStepAmount(
    steps: Long,
    formatMode: FormatMode,
): String {
    val numberFormatter = NumberFormat.getInstance(Locale.getDefault())
    when (formatMode) {
        FormatMode.Short -> {
            val shortNumber = steps.floorDiv(1_000)
            if (shortNumber < 1_000L) {
                return "${numberFormatter.format(shortNumber)}K"
            }
            return "${numberFormatter.format(shortNumber.floorDiv(1_000))}M"
        }

        FormatMode.Medium -> {
            if (steps < 1_000L) {
                return "${numberFormatter.format(steps)}"
            }
            val shortNumber = steps.floorDiv(1_000)
            if (shortNumber < 10_000L) {
                return "${numberFormatter.format(shortNumber)}K"
            }
            return "${numberFormatter.format(shortNumber.floorDiv(1_000))}M"
        }

        FormatMode.Long -> {
            return numberFormatter.format(steps)
        }
    }
}

actual fun formatInstant(
    instant: Instant,
    formatMode: FormatMode,
): String {
    val format = when (formatMode) {
        FormatMode.Short -> DateFormat.SHORT
        FormatMode.Medium -> DateFormat.MEDIUM
        FormatMode.Long -> DateFormat.LONG
    }

    val date = Date.from(instant.toJavaInstant())
    val formattedDate = DateFormat
        .getDateInstance(
            format,
            Locale.getDefault(),
        )
        .format(date)
    val formattedTime = DateFormat
        .getTimeInstance(
            DateFormat.MEDIUM,
            Locale.getDefault(),
        )
        .format(date)

    return "$formattedDate, $formattedTime"
}