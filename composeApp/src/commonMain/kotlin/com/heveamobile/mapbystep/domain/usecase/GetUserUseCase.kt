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
            .getUserWithStepDataFlow()
            .onEach { user ->
                // onEach is called on an empty list with a null value, so this makes sure there is always a user
                if (user == null) {
                    userRepository.createUser()
                }
            }
    }

    suspend fun getOneShotUser(): User {
        val user = userRepository
            .getUserFlow()
            .first()

        if (user == null) {
            userRepository.createUser()
            return getOneShotUser()
        } else {
            return user
        }
    }
}