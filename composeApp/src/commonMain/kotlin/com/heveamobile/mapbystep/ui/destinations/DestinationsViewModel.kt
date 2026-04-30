package com.heveamobile.mapbystep.ui.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DestinationsViewModel(
    getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DestinationsState())
    val state: StateFlow<DestinationsState> = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            getMapsWithProgressUseCase().collectLatest { maps ->
                _state.update { state ->
                    state.copy(
                        selectedMap = maps.first { map -> map.isActive },
                        maps = maps,
                        isLoading = false,
                    )
                }
            }
        }

    }

    fun onAction(action: DestinationsAction) {
        when (action) {
            is DestinationsAction.ToggleMapSelector -> TODO()
            is DestinationsAction.SelectMap -> {
                _state.update { it.copy(selectedMap = action.map) }
            }

            is DestinationsAction.ToggleProgressDisplay -> {
                _state.update { it.copy(isProgressExpanded = !it.isProgressExpanded) }
            }

            is DestinationsAction.ViewDestinationDetails -> TODO()
        }
    }
}