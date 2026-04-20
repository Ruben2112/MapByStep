package com.heveamobile.mapbystep

enum class FormatMode {
    Short,
    Medium,
    Long
}

expect fun formatStepAmount(
    steps: Long,
    formatMode: FormatMode = FormatMode.Long,
): String