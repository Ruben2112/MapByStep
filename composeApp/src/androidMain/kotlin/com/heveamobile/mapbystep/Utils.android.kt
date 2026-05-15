package com.heveamobile.mapbystep

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.sql.Date
import java.text.DateFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.time.Instant
import kotlin.time.toJavaInstant

actual fun formatAmount(
    amount: Long,
    formatMode: FormatMode,
): String {
    val numberFormatter = NumberFormat.getInstance(Locale.getDefault())
    when (formatMode) {
        FormatMode.Short -> {
            val shortNumber = amount.floorDiv(1_000)
            if (shortNumber < 1_000L) {
                return "${numberFormatter.format(shortNumber)}K"
            }
            return "${numberFormatter.format(shortNumber.floorDiv(1_000))}M"
        }

        FormatMode.Medium -> {
            if (amount < 1_000L) {
                return "${numberFormatter.format(amount)}"
            }
            val shortNumber = amount.floorDiv(1_000)
            if (shortNumber < 10_000L) {
                return "${numberFormatter.format(shortNumber)}K"
            }
            return "${numberFormatter.format(shortNumber.floorDiv(1_000))}M"
        }

        FormatMode.Long -> {
            return numberFormatter.format(amount)
        }
    }
}

actual fun formatAmount(
    amount: Int,
    formatMode: FormatMode,
): String {
    val numberFormatter = NumberFormat.getInstance(Locale.getDefault())
    when (formatMode) {
        FormatMode.Short -> {
            val shortNumber = amount.floorDiv(1_000)
            if (shortNumber < 1_000L) {
                return "${numberFormatter.format(shortNumber)}K"
            }
            return "${numberFormatter.format(shortNumber.floorDiv(1_000))}M"
        }

        FormatMode.Medium -> {
            if (amount < 1_000L) {
                return "${numberFormatter.format(amount)}"
            }
            val shortNumber = amount.floorDiv(1_000)
            if (shortNumber < 10_000L) {
                return "${numberFormatter.format(shortNumber)}K"
            }
            return "${numberFormatter.format(shortNumber.floorDiv(1_000))}M"
        }

        FormatMode.Long -> {
            return numberFormatter.format(amount)
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

actual fun formatPopulation(
    population: Int,
): String {
    val numberFormatter = NumberFormat.getInstance(Locale.getDefault())
    return numberFormatter.format(population)
}

actual fun encodeUrl(url: String): String {
    return URLEncoder
        .encode(
            url,
            StandardCharsets.UTF_8.toString(),
        )
        .replace(
            "+",
            "",
        )
}