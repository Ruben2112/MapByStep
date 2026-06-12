package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.repository.FileRepository

class ImportDatabaseUseCase(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        return fileRepository.importDatabaseFile()
    }
}
