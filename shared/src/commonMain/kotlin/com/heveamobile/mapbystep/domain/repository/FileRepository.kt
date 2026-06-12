package com.heveamobile.mapbystep.domain.repository

interface FileRepository {
    suspend fun exportDatabaseFile(): Result<Unit>
    suspend fun importDatabaseFile(): Result<Unit>
}