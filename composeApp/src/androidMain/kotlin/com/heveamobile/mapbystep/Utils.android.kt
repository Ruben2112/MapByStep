package com.heveamobile.mapbystep

import java.text.NumberFormat
import java.util.Locale

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