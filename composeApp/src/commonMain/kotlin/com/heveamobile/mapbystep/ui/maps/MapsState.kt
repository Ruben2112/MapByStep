package com.heveamobile.mapbystep.ui.maps

import com.heveamobile.mapbystep.domain.model.Map

data class MapsState(
    val activeMap: Map? = null,
    val availableMaps: List<Map> = emptyList(),
    val expandedMapId: String? = null,

    val availableSteps: Long = 0L,

    val isLoading: Boolean = false,
)

sealed interface MapsAction {
    data class ViewProgress(val map: Map) : MapsAction
    data class SetActive(val map: Map) : MapsAction
    data class ExpandProgress(val map: Map) : MapsAction
}