package com.gumamobile.mapbystep.di

import com.gumamobile.mapbystep.data.source.HealthDataSource
import com.gumamobile.mapbystep.data.source.android.HealthConnectManager
import com.gumamobile.mapbystep.data.source.local.AppDatabase
import com.gumamobile.mapbystep.data.source.local.getDatabaseBuilder
import com.gumamobile.mapbystep.domain.AndroidHealthPermissionManager
import com.gumamobile.mapbystep.domain.HealthPermissionManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(get()).build() }
    single { get<AppDatabase>().getUserDao() }
    single { get<AppDatabase>().getStepDataDao() }

    singleOf(::AndroidHealthPermissionManager).bind(HealthPermissionManager::class)
    singleOf(::HealthConnectManager).bind(HealthDataSource::class)
}