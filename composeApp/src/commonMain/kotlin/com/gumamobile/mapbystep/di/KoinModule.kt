package com.gumamobile.mapbystep.di

import com.gumamobile.mapbystep.navigation.Route
import com.gumamobile.mapbystep.ui.profile.ProfileScreen
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val appModule = module {
    navigation<Route.Profile> { route ->
        ProfileScreen()
    }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}