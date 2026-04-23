package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetMapsWithProgressUseCase(val mapRepository: MapRepository) {
    operator fun invoke(): Flow<List<Map>> {
        return mapRepository.getAllWithProgressFlow()
    }
}