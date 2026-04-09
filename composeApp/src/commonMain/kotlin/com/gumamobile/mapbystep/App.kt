package com.gumamobile.mapbystep

import LocalSpacing
import Spacing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import spacing

@Composable
@Preview
fun App() {
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme {

        }
    }
}