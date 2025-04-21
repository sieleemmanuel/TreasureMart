package com.sielehub.treasuremart.domain.use_case.account

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(userId: Int): Flow<Resource<User?>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(authRepository.getUser(userId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}