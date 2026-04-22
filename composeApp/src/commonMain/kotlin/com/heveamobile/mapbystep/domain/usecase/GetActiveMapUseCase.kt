package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetActiveMapUseCase(val mapRepository: MapRepository) {
    operator fun invoke(): Flow<Map?> {
        return mapRepository.getActiveMapFlow()
    }
}