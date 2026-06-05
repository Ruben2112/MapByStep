package com.heveamobile.mapbystep.ui.settings

data class SettingsState(
    val distanceMultiplier: Double = 1.0,
)

sealed interface SettingsAction {
    data class UpdateDistanceMultiplier(val distanceMultiplier: Float) : SettingsAction
}