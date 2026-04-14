package com.gumamobile.mapbystep.di

import com.gumamobile.mapbystep.navigation.Route
import com.gumamobile.mapbystep.ui.maps.MapsScreen
import com.gumamobile.mapbystep.ui.profile.ProfileScreen
import com.gumamobile.mapbystep.ui.profile.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val appModule = module {

    viewModelOf(::ProfileViewModel)

    navigation<Route.Profile> { ProfileScreen(viewModel = koinViewModel()) }
    navigation<Route.Maps> { MapsScreen() }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}