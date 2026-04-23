package com.heveamobile.mapbystep.di

import com.heveamobile.mapbystep.data.repository.DestinationRepositoryImpl
import com.heveamobile.mapbystep.data.repository.MapRepositoryImpl
import com.heveamobile.mapbystep.data.repository.StepDataRepositoryImpl
import com.heveamobile.mapbystep.data.repository.UserRepositoryImpl
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.repository.MapRepository
import com.heveamobile.mapbystep.domain.repository.StepDataRepository
import com.heveamobile.mapbystep.domain.repository.UserRepository
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.heveamobile.mapbystep.domain.usecase.UpdateUserRecordsUseCase
import com.heveamobile.mapbystep.domain.usecase.UpsertInitialMapDataUseCase
import com.heveamobile.mapbystep.navigation.Route
import com.heveamobile.mapbystep.ui.home.HomeViewModel
import com.heveamobile.mapbystep.ui.maps.MapsScreen
import com.heveamobile.mapbystep.ui.maps.MapsViewModel
import com.heveamobile.mapbystep.ui.profile.ProfileScreen
import com.heveamobile.mapbystep.ui.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val navigationModule = module {
    navigation<Route.Profile> { ProfileScreen() }
    navigation<Route.Maps> { MapsScreen() }
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MapsViewModel)
}

val useCaseModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::SyncStepsUseCase)
    factoryOf(::UpdateUserRecordsUseCase)
    factoryOf(::UpsertInitialMapDataUseCase)
    factoryOf(::GetMapsWithProgressUseCase)
}

val repositoryModule = module {
    singleOf(::StepDataRepositoryImpl) { bind<StepDataRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::MapRepositoryImpl) { bind<MapRepository>() }
    singleOf(::DestinationRepositoryImpl) { bind<DestinationRepository>() }
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