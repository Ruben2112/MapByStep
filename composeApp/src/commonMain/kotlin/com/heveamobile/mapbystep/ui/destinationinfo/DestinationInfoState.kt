package com.heveamobile.mapbystep.ui.destinationinfo

import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Info
import com.heveamobile.mapbystep.domain.model.Map

data class DestinationInfoState(
    val isLoading: Boolean = false,

    val maps: List<Map> = emptyList(),
    val selectedMap: Map? = null,
    val destinations: List<Destination> = emptyList(),
    val selectedDestination: Destination? = null,
    val info: Info? = null,
)

sealed interface DestinationInfoAction {
    data class SelectMap(val map: Map) : DestinationInfoAction
    data class SelectDestination(val destination: Destination) : DestinationInfoAction
}