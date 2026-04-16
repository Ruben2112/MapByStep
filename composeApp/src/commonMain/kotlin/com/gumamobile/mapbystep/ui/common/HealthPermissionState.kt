package com.gumamobile.mapbystep.ui.common

sealed class HealthPermissionState {
    data object Loading : HealthPermissionState()
    data object NotInstalled : HealthPermissionState()
    data object NotGranted : HealthPermissionState()
    data object Granted : HealthPermissionState()
}