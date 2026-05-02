package com.heveamobile.mapbystep.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.heveamobile.mapbystep.domain.repository.UserPreferencesRepository
import com.heveamobile.mapbystep.ui.home.SortingOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesRepository {
    private object Keys {
        val GRID_SORTING_ORDER = stringPreferencesKey("grid_sorting_order")
        val HIDE_UNDISCOVERED = booleanPreferencesKey("hide_undiscovered")
    }

    override val gridSortingOrder: Flow<SortingOrder> = dataStore.data.map { prefs ->
        SortingOrder.valueOf(
            prefs[Keys.GRID_SORTING_ORDER]
                ?: SortingOrder.Rarity.name,
        )
    }

    override val hideUndiscovered: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[Keys.HIDE_UNDISCOVERED]
            ?: false
    }

    override suspend fun updateGridSortingOrder(sortingOrder: SortingOrder) {
        dataStore.edit { it[Keys.GRID_SORTING_ORDER] = sortingOrder.name }
    }

    override suspend fun updateHideUndiscovered(hideUndiscovered: Boolean) {
        dataStore.edit { it[Keys.HIDE_UNDISCOVERED] = hideUndiscovered }
    }
}