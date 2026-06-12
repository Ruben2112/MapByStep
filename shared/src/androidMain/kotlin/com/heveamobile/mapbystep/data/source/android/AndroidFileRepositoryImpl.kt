package com.heveamobile.mapbystep.data.source.android

import android.content.Context
import androidx.core.net.toUri
import com.heveamobile.mapbystep.data.source.local.AppDatabase
import com.heveamobile.mapbystep.data.source.local.DATABASE_FILE_NAME
import com.heveamobile.mapbystep.di.getKoinModules
import com.heveamobile.mapbystep.domain.repository.FilePickerHandler
import com.heveamobile.mapbystep.domain.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.io.FileInputStream
import java.io.FileOutputStream

class AndroidFileRepositoryImpl(
    private val context: Context,
    private val database: AppDatabase,
    private val filePickerHandler: FilePickerHandler,
) : FileRepository {

    override suspend fun exportDatabaseFile(): Result<Unit> = runCatching {
        val dbFile = context.getDatabasePath(DATABASE_FILE_NAME)

        val destinationUriString = filePickerHandler.getExportLocation(DATABASE_FILE_NAME)
            ?: throw Exception("User cancelled export")
        val destinationUri = destinationUriString.toUri()

        withContext(Dispatchers.IO) {
            // Force a checkpoint. This ensures all WAL data is in the .db file.
            val cursor = database.query(
                "PRAGMA wal_checkpoint(FULL)",
                null,
            )
            cursor.moveToFirst()
            cursor.close()

            context.contentResolver
                .openOutputStream(destinationUri)
                ?.use { outputStream ->
                    FileInputStream(dbFile).use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                ?: throw Exception("Could not open output stream")
        }
    }

    override suspend fun importDatabaseFile(): Result<Unit> = runCatching {
        val dbFile = context.getDatabasePath(DATABASE_FILE_NAME)

        val sourceUriString = filePickerHandler.getImportLocation()
            ?: throw Exception("User cancelled import")
        val sourceUri = sourceUriString.toUri()

        withContext(Dispatchers.IO) {
            database.close()

            context.contentResolver
                .openInputStream(sourceUri)
                ?.use { inputStream ->
                    FileOutputStream(dbFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                ?: throw Exception("Could not open input stream")

            // Also delete -wal and -shm files if they exist to prevent corruption
            val walFile = context.getDatabasePath("$DATABASE_FILE_NAME-wal")
            val shmFile = context.getDatabasePath("$DATABASE_FILE_NAME-shm")
            if (walFile.exists()) walFile.delete()
            if (shmFile.exists()) shmFile.delete()

            // Reload Koin modules to refresh the database instance and repositories
            unloadKoinModules(getKoinModules())
            loadKoinModules(getKoinModules())
        }
    }
}
