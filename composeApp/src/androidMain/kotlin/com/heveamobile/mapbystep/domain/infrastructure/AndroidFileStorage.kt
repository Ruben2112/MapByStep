package com.heveamobile.mapbystep.domain.infrastructure

import android.content.Context
import java.io.File

class AndroidFileStorage(private val context: Context) : FileStorage {
    override val basePath: String = context.filesDir.absolutePath

    override suspend fun saveFile(
        relativePath: String,
        bytes: ByteArray,
    ): String {
        val file = File(
            context.filesDir,
            relativePath,
        )
        file.parentFile?.mkdirs()
        file.writeBytes(bytes)
        return file.absolutePath
    }

    override fun getAbsolutePath(relativePath: String): String {
        return File(
            context.filesDir,
            relativePath,
        ).absolutePath
    }

    override fun exists(relativePath: String): Boolean = File(
        context.filesDir,
        relativePath,
    ).exists()
}