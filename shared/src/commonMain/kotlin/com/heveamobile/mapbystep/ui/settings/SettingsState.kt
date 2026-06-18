package com.heveamobile.mapbystep.ui.settings

data class SettingsState(
    val distanceMultiplier: Double = 1.0,
    val showImportConfirmationAlert: Boolean = false,
)

sealed interface SettingsAction {
    data class UpdateDistanceMultiplier(val distanceMultiplier: Float) : SettingsAction
    data object ExportProgress : SettingsAction
    data object ImportProgress : SettingsAction
    data object ConfirmImport : SettingsAction
    data object CancelImport : SettingsAction
}

sealed interface SettingsEvent {
    data object ExportSuccessful : SettingsEvent
    data object ExportFailed : SettingsEvent
    data object ImportSuccessful : SettingsEvent
    data object ImportFailed : SettingsEvent
}