package com.gumamobile.mapbystep.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.LoadSteps -> loadSteps()
        }
    }

    private fun loadSteps() {
        viewModelScope.launch {
            //TODO: Load steps from Health Connect
        }
    }
}