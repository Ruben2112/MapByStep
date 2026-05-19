package com.heveamobile.mapbystep.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapsViewModel(
    val getUserUseCase: GetUserUseCase,
    val getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MapsState())
    val state: StateFlow<MapsState> = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            getUserUseCase().collectLatest { user ->
                _state.update {
                    it.copy(
                        availableSteps = user?.availableSteps
                            ?: 0L,
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getMapsWithProgressUseCase()
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .collectLatest { maps ->
                    _state.update { state ->
                        state.copy(
                            maps = maps,
                            expandedMapId = state.expandedMapId
                                ?: maps.first().id,
                            isLoading = false,
                        )
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase().first()
            getMapsWithProgressUseCase().first()

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onAction(action: MapsAction) {
        when (action) {
            is MapsAction.ViewProgress -> TODO()
            is MapsAction.ExpandProgress -> {
                _state.update {
                    it.copy(
                        expandedMapId = if (it.expandedMapId != action.map.id) action.map.id else null,
                    )
                }
            }
        }
    }
}