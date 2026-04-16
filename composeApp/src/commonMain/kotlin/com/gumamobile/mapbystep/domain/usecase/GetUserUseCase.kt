package com.gumamobile.mapbystep.domain.usecase

import com.gumamobile.mapbystep.domain.model.User
import com.gumamobile.mapbystep.domain.repository.UserRepository
import kotlin.time.Clock

class GetUserUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<User> = runCatching {
        userRepository.getUser()
            ?: userRepository.createUser(Clock.System.now())
    }
}