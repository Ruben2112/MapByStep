package com.heveamobile.mapbystep.platform.manager

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import com.heveamobile.mapbystep.domain.manager.HealthPermissionManager
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus

class AndroidHealthPermissionManager(private val context: Context) : HealthPermissionManager {
    private val client by lazy { HealthConnectClient.getOrCreate(context) }

    override fun getRequiredPermissions(): Set<String> {
        return setOf(
            HealthPermission.getReadPermission(StepsRecord::class),
            "android.permission.health.READ_HEALTH_DATA_HISTORY",
            "android.permission.health.READ_HEALTH_DATA_IN_BACKGROUND",
        )
    }

    override suspend fun checkPermissionState(): HealthPermissionStatus {
        val availability = HealthConnectClient.getSdkStatus(context)
        if (availability == HealthConnectClient.SDK_UNAVAILABLE) return HealthPermissionStatus.NotInstalled

        val granted = client.permissionController.getGrantedPermissions()
        return if (granted.containsAll(getRequiredPermissions())) {
            HealthPermissionStatus.Granted
        } else {
            HealthPermissionStatus.NotGranted
        }
    }

    fun createContract() = PermissionController.createRequestPermissionResultContract()
}