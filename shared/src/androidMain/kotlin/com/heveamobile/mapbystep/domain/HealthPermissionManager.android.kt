package com.heveamobile.mapbystep.domain

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable

@Composable
actual fun rememberHealthPermissionLauncher(
    manager: HealthPermissionManager,
    onResult: () -> Unit,
): () -> Unit {
    val permissionManager = manager as? AndroidHealthPermissionManager
        ?: return {}

    val launcher = rememberLauncherForActivityResult(
        contract = permissionManager.createContract(),
    ) { _ ->
        onResult()
    }

    return {
        launcher.launch(permissionManager.getRequiredPermissions())
    }
}