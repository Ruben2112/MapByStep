package com.heveamobile.mapbystep.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Storage
import androidx.datastore.preferences.core.Preferences

fun createDataStore(storage: Storage<Preferences>): DataStore<Preferences> =
    DataStoreFactory.create(storage = storage)

internal const val dataStoreFileName = "user_preferences.preferences_pb"