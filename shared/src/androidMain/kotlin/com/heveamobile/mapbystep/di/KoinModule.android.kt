package com.heveamobile.mapbystep.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.heveamobile.mapbystep.data.source.android.AndroidFilePickerHandler
import com.heveamobile.mapbystep.data.source.android.AndroidFileRepositoryImpl
import com.heveamobile.mapbystep.data.source.android.HealthConnectManager
import com.heveamobile.mapbystep.data.source.local.AppDatabase
import com.heveamobile.mapbystep.data.source.local.getDatabaseBuilder
import com.heveamobile.mapbystep.data.source.remote.HealthDataSource
import com.heveamobile.mapbystep.domain.AndroidHealthPermissionManager
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import com.heveamobile.mapbystep.domain.repository.FilePickerHandler
import com.heveamobile.mapbystep.domain.repository.FileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.onClose

// DataStore must be a process-level singleton to avoid "multiple DataStores active" error.
// We cache it here so it survives Koin module reloads during database import.
private var dataStoreInstance: DataStore<Preferences>? = null

const val PREFS_FILE_NAME = "user_prefs.preferences_pb"

actual val targetModule = module {
    single { getDatabaseBuilder(get()).build() } onClose { it?.close() }

    single<DataStore<Preferences>> {
        // Reuse the existing DataStore instance if it exists to avoid IllegalStateException
        // and ensure the CoroutineScope remains active after module reloads.
        dataStoreInstance
            ?: PreferenceDataStoreFactory
                .create(
                    produceFile = { androidContext().filesDir.resolve(PREFS_FILE_NAME) },
                )
                .also { dataStoreInstance = it }
    }

    single { get<AppDatabase>().getUserDao() }
    single { get<AppDatabase>().getStepDataDao() }
    single { get<AppDatabase>().getMapDao() }
    single { get<AppDatabase>().getDestinationDao() }
    single { get<AppDatabase>().getInfoDao() }

    singleOf(::AndroidFilePickerHandler) { bind<FilePickerHandler>() }
    singleOf(::AndroidFileRepositoryImpl) { bind<FileRepository>() }

    singleOf(::AndroidHealthPermissionManager).bind(HealthPermissionManager::class)
    singleOf(::HealthConnectManager).bind(HealthDataSource::class)
}
