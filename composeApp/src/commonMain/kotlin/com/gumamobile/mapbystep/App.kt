package com.gumamobile.mapbystep

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gumamobile.mapbystep.theme.MapByStepTheme
import com.gumamobile.mapbystep.ui.main.MainScreen

@Composable
@Preview
fun App() {
    MapByStepTheme {
        MainScreen()
    }
}