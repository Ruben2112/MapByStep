package com.heveamobile.mapbystep.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.distanceMultiplier.collectLatest { multiplier ->
                _state.update { it.copy(distanceMultiplier = multiplier) }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.UpdateDistanceMultiplier -> {
                viewModelScope.launch {
                    // Round to 1 decimal place to fix floating point precision errors
                    val roundedValue = kotlin.math.round(action.distanceMultiplier * 10F) / 10.0
                    _state.update { it.copy(distanceMultiplier = roundedValue) }
                    userPreferencesRepository.updateDistanceMultiplier(roundedValue)
                }
            }
        }
    }
}