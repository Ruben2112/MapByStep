package com.gumamobile.mapbystep.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.tomorrow_regular
import org.jetbrains.compose.resources.Font

val Tomorrow
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.tomorrow_regular,
            weight = FontWeight.Normal,
        ),
    )

val Typography: Typography
    @Composable get() = Typography(
        titleMedium = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 24.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 12.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 14.sp,
            lineHeight = 16.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 20.sp,
        ),
    )