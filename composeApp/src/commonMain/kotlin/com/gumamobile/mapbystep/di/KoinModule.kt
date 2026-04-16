package com.gumamobile.mapbystep.di

import com.gumamobile.mapbystep.data.repository.StepDataRepositoryImpl
import com.gumamobile.mapbystep.data.repository.UserRepositoryImpl
import com.gumamobile.mapbystep.domain.repository.StepDataRepository
import com.gumamobile.mapbystep.domain.repository.UserRepository
import com.gumamobile.mapbystep.domain.usecase.GetUserUseCase
import com.gumamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.gumamobile.mapbystep.navigation.Route
import com.gumamobile.mapbystep.ui.main.HomeViewModel
import com.gumamobile.mapbystep.ui.maps.MapsScreen
import com.gumamobile.mapbystep.ui.profile.ProfileScreen
import com.gumamobile.mapbystep.ui.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val navigationModule = module {
    navigation<Route.Profile> { ProfileScreen() }
    navigation<Route.Maps> { MapsScreen() }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            get(),
            get(),
        )
    }
    viewModel {
        ProfileViewModel(
            get(),
            get(),
        )
    }
}

val useCaseModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::SyncStepsUseCase)
}

val repositoryModule = module {
    singleOf(::StepDataRepositoryImpl) { bind<StepDataRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(
            navigationModule,
            viewModelModule,
            useCaseModule,
            repositoryModule,
            targetModule,
        )
    }
}