package com.heveamobile.mapbystep.di

import com.heveamobile.mapbystep.data.repository.CountryInfoRepositoryImpl
import com.heveamobile.mapbystep.data.repository.DestinationRepositoryImpl
import com.heveamobile.mapbystep.data.repository.MapRepositoryImpl
import com.heveamobile.mapbystep.data.repository.StepDataRepositoryImpl
import com.heveamobile.mapbystep.data.repository.UserPreferencesRepositoryImpl
import com.heveamobile.mapbystep.data.repository.UserRepositoryImpl
import com.heveamobile.mapbystep.domain.repository.CountryInfoRepository
import com.heveamobile.mapbystep.domain.repository.DestinationInfoRepository
import com.heveamobile.mapbystep.domain.repository.DestinationInfoRepositoryResolver
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.repository.MapRepository
import com.heveamobile.mapbystep.domain.repository.StepDataRepository
import com.heveamobile.mapbystep.domain.repository.UserPreferencesRepository
import com.heveamobile.mapbystep.domain.repository.UserRepository
import com.heveamobile.mapbystep.domain.usecase.GetCountOfDirectionsInStockUseCase
import com.heveamobile.mapbystep.domain.usecase.GetDailyStepsChartDataUseCase
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.PurchaseDirectionsUseCase
import com.heveamobile.mapbystep.domain.usecase.SpendStepsUseCase
import com.heveamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.heveamobile.mapbystep.domain.usecase.UpdateUserRecordsUseCase
import com.heveamobile.mapbystep.domain.usecase.UpsertInitialMapDataUseCase
import com.heveamobile.mapbystep.navigation.NavigationHandler
import com.heveamobile.mapbystep.navigation.Route
import com.heveamobile.mapbystep.ui.destinationinfo.DestinationInfoScreen
import com.heveamobile.mapbystep.ui.destinationinfo.DestinationInfoViewModel
import com.heveamobile.mapbystep.ui.destinations.DestinationsScreen
import com.heveamobile.mapbystep.ui.destinations.DestinationsViewModel
import com.heveamobile.mapbystep.ui.directions.DirectionsScreen
import com.heveamobile.mapbystep.ui.directions.DirectionsViewModel
import com.heveamobile.mapbystep.ui.home.HomeViewModel
import com.heveamobile.mapbystep.ui.maps.MapsScreen
import com.heveamobile.mapbystep.ui.maps.MapsViewModel
import com.heveamobile.mapbystep.ui.profile.ProfileScreen
import com.heveamobile.mapbystep.ui.profile.ProfileViewModel
import com.heveamobile.mapbystep.ui.settings.SettingsScreen
import com.heveamobile.mapbystep.ui.settings.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val navigationModule = module {
    singleOf(::NavigationHandler)
    navigation<Route.Profile> { ProfileScreen() }
    navigation<Route.Maps> { MapsScreen() }
    navigation<Route.Destinations> { DestinationsScreen() }
    navigation<Route.DestinationInfo> { route ->
        DestinationInfoScreen(
            viewModel = koinViewModel { parametersOf(route) },
            route = route,
        )
    }
    navigation<Route.Directions> { DirectionsScreen() }
    navigation<Route.Settings> { SettingsScreen() }
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MapsViewModel)
    viewModelOf(::DestinationsViewModel)
    viewModel { _ ->
        DestinationInfoViewModel(
            destinationRepository = get(),
            destinationInfoRepository = get(),
            getMapsWithProgressUseCase = get(),
        )
    }
    viewModelOf(::DirectionsViewModel)
    viewModelOf(::SettingsViewModel)
}

val useCaseModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::SyncStepsUseCase)
    factoryOf(::UpdateUserRecordsUseCase)
    factoryOf(::UpsertInitialMapDataUseCase)
    factoryOf(::GetMapsWithProgressUseCase)
    factoryOf(::SpendStepsUseCase)
    factoryOf(::GetCountOfDirectionsInStockUseCase)
    factoryOf(::PurchaseDirectionsUseCase)
    factoryOf(::GetDailyStepsChartDataUseCase)
}

val repositoryModule = module {
    singleOf(::StepDataRepositoryImpl) { bind<StepDataRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::MapRepositoryImpl) { bind<MapRepository>() }
    singleOf(::DestinationRepositoryImpl) { bind<DestinationRepository>() }
    singleOf(::UserPreferencesRepositoryImpl) { bind<UserPreferencesRepository>() }

    singleOf(::CountryInfoRepositoryImpl) {
        bind<CountryInfoRepository>()
        bind<DestinationInfoRepository>()
    }

    singleOf(::DestinationInfoRepositoryResolver)
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