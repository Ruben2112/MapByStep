package com.heveamobile.mapbystep.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.heveamobile.mapbystep.data.source.android.HealthConnectManager
import com.heveamobile.mapbystep.data.source.local.AppDatabase
import com.heveamobile.mapbystep.data.source.local.getDatabaseBuilder
import com.heveamobile.mapbystep.data.source.remote.HealthDataSource
import com.heveamobile.mapbystep.domain.AndroidHealthPermissionManager
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(get()).build() }
    single {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().filesDir.resolve("user_prefs.preferences_pb") },
        )
    }
    single { get<AppDatabase>().getUserDao() }
    single { get<AppDatabase>().getStepDataDao() }
    single { get<AppDatabase>().getMapDao() }
    single { get<AppDatabase>().getDestinationDao() }
    single { get<AppDatabase>().getInfoDao() }

    singleOf(::AndroidHealthPermissionManager).bind(HealthPermissionManager::class)
    singleOf(::HealthConnectManager).bind(HealthDataSource::class)
}