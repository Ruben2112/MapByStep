package com.heveamobile.mapbystep.ui.common

sealed class HealthPermissionStatus {
    data object Loading : HealthPermissionStatus()
    data object NotInstalled : HealthPermissionStatus()
    data object NotGranted : HealthPermissionStatus()
    data object Granted : HealthPermissionStatus()
}