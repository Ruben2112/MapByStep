package com.heveamobile.mapbystep.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.usecase.GetActiveMapUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
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
    val getActiveMapUseCase: GetActiveMapUseCase,
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

        viewModelScope.launch {
            getActiveMapUseCase()
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .collectLatest { map ->
                    _state.update { state ->
                        state.copy(
                            activeMap = map,
                            expandedMapId = state.expandedMapId
                                ?: map?.id,
                            isLoading = false,
                        )
                    }
                }
        }

        viewModelScope.launch {
            getUserUseCase().first()
            getActiveMapUseCase().first()

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onAction(action: MapsAction) {
        when (action) {
            is MapsAction.SetActive -> TODO()
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