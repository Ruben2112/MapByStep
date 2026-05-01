package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.ui.home.SortingOrder
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val gridSortingOrder: Flow<SortingOrder>
    val hideUndiscovered: Flow<Boolean>

    suspend fun updateGridSortingOrder(sortingOrder: SortingOrder)
    suspend fun updateHideUndiscovered(hideUndiscovered: Boolean)
}