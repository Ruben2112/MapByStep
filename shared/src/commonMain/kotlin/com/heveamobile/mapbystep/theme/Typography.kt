package com.heveamobile.mapbystep.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mapbystep.shared.generated.resources.Res
import mapbystep.shared.generated.resources.tomorrow_regular
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
        titleLarge = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 24.sp,
            color = OnSurface,
        ),
        titleMedium = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 18.sp,
            color = OnSurface,
        ),
        titleSmall = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 14.sp,
            color = OnSurface,
        ),
        bodySmall = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 12.sp,
            color = OnSurface,
        ),
        bodyMedium = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            color = OnSurface,
        ),
        bodyLarge = TextStyle(
            fontFamily = Tomorrow,
            fontSize = 20.sp,
            color = OnSurface,
        ),
    )