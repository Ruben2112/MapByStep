package com.heveamobile.mapbystep.ui.maps

import com.heveamobile.mapbystep.domain.model.Map

data class MapsState(
    val maps: List<Map> = emptyList(),
    val expandedMapId: String? = null,

    val availableSteps: Long = 0L,

    val isLoading: Boolean = false,
)

sealed interface MapsAction {
    data class ViewProgress(val map: Map) : MapsAction
    data class ExpandProgress(val map: Map) : MapsAction
}