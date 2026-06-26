package com.heveamobile.mapbystep.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.heveamobile.mapbystep.domain.model.Rarity

private val Brown50 = Color(color = 0xFFFBFAF8)
private val Brown400 = Color(color = 0xFFAF9797)
private val Brown600 = Color(color = 0xFF534340)

private val Gray100 = Color(color = 0xFFFFFFFF)
private val Gray200 = Color(color = 0xFFDFDFDF)
private val Gray300 = Color(color = 0xFFBFBFBF)
private val Gray400 = Color(color = 0xFF9F9F9F)
private val Gray500 = Color(color = 0xFF808080)
private val Gray600 = Color(color = 0xFF534340)
private val Gray700 = Color(color = 0xFF404040)
private val Gray800 = Color(color = 0xFF202020)
private val Gray900 = Color(color = 0xFF000000)

private val Green50 = Color(color = 0xFFF5F8ED)
private val Green100 = Color(color = 0xFFE7EFD8)
private val Green200 = Color(color = 0xFFC5D8A4)

val Background = Green100
val SurfaceContainer = Green50
val SurfaceContainerHigh = Green200
val OnSurface = Brown600
val Outline = Green200
val PrimaryContainer = Brown50
val OnPrimaryContainer = Brown600
val SecondaryContainer = Green100
val OnSecondaryContainer = Brown600
val TertiaryContainer = Gray900
val OnTertiaryContainer = Brown400

val OutlineVariant = Brown600
val DisabledContainer = Gray200
val DisabledContainerOutline = Gray500
val OnDisabledContainer = Gray500

val Error = Color(0xFFA92220)
val RarityCommon = Brown600
val RarityUncommon = Color(0xFF2AA120)
val RarityRare = Color(0xFF2A22A0)
val RarityEpic = Color(0xFFA922A0)
val RarityLegendary = Color(0xFFA96120)

val Rarity.color: Color
    get() = when (this) {
        Rarity.Common -> RarityCommon
        Rarity.Uncommon -> RarityUncommon
        Rarity.Rare -> RarityRare
        Rarity.Epic -> RarityEpic
        Rarity.Legendary -> RarityLegendary
    }

@Composable
fun switchColors(): SwitchColors {
    return SwitchDefaults
        .colors()
        .copy(
            uncheckedBorderColor = MaterialTheme.colorScheme.onTertiaryContainer,
            uncheckedThumbColor = MaterialTheme.colorScheme.onTertiaryContainer,
            uncheckedTrackColor = MaterialTheme.colorScheme.primaryContainer,
            checkedBorderColor = MaterialTheme.colorScheme.onSurface,
            checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
            checkedTrackColor = MaterialTheme.colorScheme.onSurface,
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timePickerColors(): TimePickerColors {
    return TimePickerDefaults
        .colors()
        .copy(
            clockDialColor = MaterialTheme.colorScheme.secondaryContainer,
            selectorColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            clockDialSelectedContentColor = MaterialTheme.colorScheme.onSurface,
            clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
            periodSelectorBorderColor = MaterialTheme.colorScheme.outline,
            periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.background,
            periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onSurface,
            periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.background,
            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onSurface,
            timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
        )
}

@Composable
fun sliderColors(): SliderColors {
    return SliderDefaults
        .colors()
        .copy(
            activeTrackColor = MaterialTheme.colorScheme.onSurface,
            inactiveTrackColor = MaterialTheme.colorScheme.onTertiaryContainer,
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent,
        )
}

fun Color.toHex(): String {
    val r = (red * 255)
        .toInt()
        .toString(16)
        .padStart(
            2,
            '0',
        )
    val g = (green * 255)
        .toInt()
        .toString(16)
        .padStart(
            2,
            '0',
        )
    val b = (blue * 255)
        .toInt()
        .toString(16)
        .padStart(
            2,
            '0',
        )
    return "#$r$g$b".uppercase()
}