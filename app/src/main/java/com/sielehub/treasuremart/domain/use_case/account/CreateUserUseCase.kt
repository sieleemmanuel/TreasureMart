package com.sielehub.treasuremart.domain.use_case.account

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.domain.model.SignupRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateUserUseCase(private val authRepository: AuthRepositoryImpl) {

    operator fun invoke(signupRequest: SignupRequest): Flow<Resource<SignupRequest?>> = flow {
        try {
            emit(Resource.Loading())
            val id = authRepository.createUser(signupRequest)
            emit(Resource.Success(data = signupRequest))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}