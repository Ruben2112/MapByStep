package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.ui.home.SortingOrder
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val gridSortingOrder: Flow<SortingOrder>
    val hideUndiscovered: Flow<Boolean>
    val distanceMultiplier: Flow<Double>

    suspend fun updateGridSortingOrder(sortingOrder: SortingOrder)
    suspend fun updateHideUndiscovered(hideUndiscovered: Boolean)
    suspend fun updateDistanceMultiplier(distanceMultiplier: Double)
}