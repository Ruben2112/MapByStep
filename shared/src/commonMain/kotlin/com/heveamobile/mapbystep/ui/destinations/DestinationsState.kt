package com.heveamobile.mapbystep.ui.destinations

import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Map

data class DestinationsState(
    val maps: List<Map> = emptyList(),
    val selectedMap: Map? = null,
    val destinations: List<Destination> = emptyList(),
    val isProgressExpanded: Boolean = false,

    val isLoading: Boolean = false,
)

sealed interface DestinationsAction {
    data object ToggleMapSelector : DestinationsAction
    data class SelectMap(val map: Map?) : DestinationsAction
    data object ToggleProgressDisplay : DestinationsAction
    data class OpenDestinationInfo(val destinationId: String) : DestinationsAction
}
