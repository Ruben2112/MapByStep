package com.gumamobile.mapbystep.domain.usecase

import com.gumamobile.mapbystep.domain.repository.StepDataRepository
import com.gumamobile.mapbystep.domain.repository.UserRepository
import kotlin.time.Clock

class SyncStepsUseCase(
    private val stepDataRepository: StepDataRepository,
    private val userRepository: UserRepository,
    private val getUserUseCase: GetUserUseCase,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        val user = getUserUseCase().getOrThrow()
        val currentTime = Clock.System.now()
        val startTime = user.lastSyncTime
            ?: user.startTime

        val newStepData = stepDataRepository.fetchRemoteSteps(
            startTime,
            currentTime,
        )
        if (newStepData.isNotEmpty()) {
            stepDataRepository.saveStepData(
                userId = user.id,
                stepData = newStepData,
            )
        }
        userRepository.updateUserSyncTime(
            userId = user.id,
            syncTime = currentTime,
        )
    }
}