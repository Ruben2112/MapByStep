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