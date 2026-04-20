package com.heveamobile.mapbystep

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.heveamobile.mapbystep.theme.MapByStepTheme
import com.heveamobile.mapbystep.ui.home.HomeScreen

@Composable
@Preview
fun App() {
    MapByStepTheme {
        HomeScreen()
    }
}