package com.heveamobile.mapbystep.domain.infrastructure

interface FileStorage {
    val basePath: String
    suspend fun saveFile(
        relativePath: String,
        bytes: ByteArray,
    ): String

    fun getAbsolutePath(relativePath: String): String
    fun exists(relativePath: String): Boolean
}