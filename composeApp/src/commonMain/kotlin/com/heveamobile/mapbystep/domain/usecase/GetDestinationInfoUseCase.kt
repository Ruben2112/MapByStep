package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.repository.DestinationInfoRepository
import com.heveamobile.mapbystep.domain.repository.DestinationRepository

class GetDestinationInfoUseCase(
    private val destinationInfoRepository: DestinationInfoRepository,
    private val destinationRepository: DestinationRepository,
) {
//    operator fun invoke(): Flow<List<Map>> {
//        return destinationInfoRepository
//            .getAllWithProgressFlow()
//    }
}