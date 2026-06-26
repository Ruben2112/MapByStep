package com.heveamobile.mapbystep.domain.manager

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import com.heveamobile.mapbystep.platform.manager.AndroidHealthPermissionManager

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