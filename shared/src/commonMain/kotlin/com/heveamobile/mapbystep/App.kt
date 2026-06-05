package com.heveamobile.mapbystep

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.svg.SvgDecoder
import com.heveamobile.mapbystep.theme.MapByStepTheme
import com.heveamobile.mapbystep.ui.home.HomeScreen

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader
            .Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    MapByStepTheme {
        HomeScreen()
    }
}