package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.User
import com.heveamobile.mapbystep.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

class GetUserUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<User?> {
        return userRepository
            .getUserFlow()
            .onEach { user ->
                // This makes sure a user is created when it doesn't exist
                if (user == null) {
                    userRepository.createUser()
                }
            }
    }

    suspend fun getOneShotUser(): User {
        val user = userRepository
            .getUserFlow()
            .first()
        return if (user == null) {
            userRepository.createUser()
            userRepository
                .getUserFlow()
                .first()!!
        } else {
            user
        }
    }
}