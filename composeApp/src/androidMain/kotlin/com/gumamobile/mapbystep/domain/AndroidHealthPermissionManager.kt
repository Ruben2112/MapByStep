package com.gumamobile.mapbystep.domain

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import com.gumamobile.mapbystep.ui.common.HealthPermissionState

class AndroidHealthPermissionManager(private val context: Context) : HealthPermissionManager {
    private val client by lazy { HealthConnectClient.getOrCreate(context) }

    override fun getRequiredPermissions(): Set<String> {
        return setOf(
            HealthPermission.getReadPermission(StepsRecord::class),
            "android.permission.health.READ_HEALTH_DATA_HISTORY",
            "android.permission.health.READ_HEALTH_DATA_IN_BACKGROUND",
        )
    }

    override suspend fun checkPermissionState(): HealthPermissionState {
        val availability = HealthConnectClient.getSdkStatus(context)
        if (availability == HealthConnectClient.SDK_UNAVAILABLE) return HealthPermissionState.NotInstalled

        val granted = client.permissionController.getGrantedPermissions()
        return if (granted.containsAll(getRequiredPermissions())) {
            HealthPermissionState.Granted
        } else {
            HealthPermissionState.NotGranted
        }
    }

    fun createContract() = PermissionController.createRequestPermissionResultContract()
}

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