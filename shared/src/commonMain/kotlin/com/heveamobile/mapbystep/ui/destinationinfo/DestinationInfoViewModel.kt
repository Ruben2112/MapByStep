package com.heveamobile.mapbystep.ui.destinationinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.repository.DestinationInfoRepository
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DestinationInfoViewModel(
    private val destinationRepository: DestinationRepository,
    private val destinationInfoRepository: DestinationInfoRepository,
    private val getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(DestinationInfoState())
    val state: StateFlow<DestinationInfoState> = _state.asStateFlow()

    fun loadDestinationInfo(destinationId: String?) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            getMapsWithProgressUseCase().collectLatest { maps ->
                if (maps.isEmpty()) {
                    _state.update { it.copy(isLoading = false) }
                    return@collectLatest
                }

                var selectedDestination: Destination? =
                    if (destinationId != null) destinationRepository.getDestinationById(destinationId) else _state.value.selectedDestination

                val selectedMap: Map? = if (selectedDestination == null) {
                    maps.firstOrNull()
                } else {
                    maps.firstOrNull { it.id == selectedDestination.mapId }
                }

                if (selectedMap == null) return@collectLatest

                if (selectedDestination == null) {
                    selectedDestination = selectedMap.destinations
                        .filter { it.isDiscovered }
                        .minByOrNull { it.name }
                }

                // Only proceed if we actually found a destination to show
                val destinationInfo = selectedDestination?.let {
                    destinationInfoRepository.getInfoById(it.infoId)
                }

                _state.update { state ->
                    state.copy(
                        maps = maps,
                        selectedMap = selectedMap,
                        destinations = selectedMap.destinations
                            .filter { it.isDiscovered }
                            .sortedBy { it.name },
                        selectedDestination = selectedDestination,
                        info = destinationInfo,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onAction(action: DestinationInfoAction) {
        when (action) {
            is DestinationInfoAction.SelectDestination -> viewModelScope.launch(Dispatchers.IO) {
                _state.update { state ->
                    val destination = action.destination
                    val destinationInfo = destinationInfoRepository.getInfoById(destination.infoId)

                    state.copy(
                        selectedDestination = action.destination,
                        info = destinationInfo,
                    )
                }
            }

            is DestinationInfoAction.SelectMap -> viewModelScope.launch(Dispatchers.IO) {
                val map = action.map
                val destination = map.destinations
                    .filter { it.isDiscovered }
                    .minByOrNull { it.name }

                val destinationInfo = destination?.let {
                    destinationInfoRepository.getInfoById(it.infoId)
                }

                _state.update { state ->
                    state.copy(
                        selectedMap = map,
                        destinations = map.destinations
                            .filter { it.isDiscovered }
                            .sortedBy { it.name },
                        selectedDestination = destination,
                        info = destinationInfo,
                    )
                }
            }
        }
    }
}