package com.gumamobile.mapbystep.domain

import androidx.compose.runtime.Composable
import com.gumamobile.mapbystep.ui.common.HealthPermissionState

interface HealthPermissionManager {
    suspend fun checkPermissionState(): HealthPermissionState
    fun getRequiredPermissions(): Set<String>

}

@Composable
expect fun rememberHealthPermissionLauncher(
    manager: HealthPermissionManager,
    onResult: () -> Unit,
): () -> Unit