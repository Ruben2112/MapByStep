package com.heveamobile.mapbystep

import kotlin.time.Instant

enum class FormatMode {
    Short,
    Medium,
    Long
}

expect fun formatStepAmount(
    steps: Long,
    formatMode: FormatMode = FormatMode.Long,
): String

expect fun formatInstant(
    instant: Instant,
    formatMode: FormatMode = FormatMode.Long,
): String

expect fun formatPopulation(
    population: Int,
): String

fun String.toTitleCase(): String {
    return this
        .split(" ")
        .joinToString(" ") { word ->
            word
                .lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}