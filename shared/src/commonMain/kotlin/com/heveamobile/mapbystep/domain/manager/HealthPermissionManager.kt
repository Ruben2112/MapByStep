package com.heveamobile.mapbystep.domain.manager

import androidx.compose.runtime.Composable
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus

interface HealthPermissionManager {
    suspend fun checkPermissionState(): HealthPermissionStatus
    fun getRequiredPermissions(): Set<String>

}

@Composable
expect fun rememberHealthPermissionLauncher(
    manager: HealthPermissionManager,
    onResult: () -> Unit,
): () -> Unit