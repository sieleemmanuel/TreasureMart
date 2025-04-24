package com.sielehub.treasuremart.domain.use_case.account

import android.util.Log
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.SignupResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateUserUseCase(private val authRepository: AuthRepositoryImpl) {

    operator fun invoke(signupRequest: SignupRequest): Flow<Resource<SignupResponse>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = authRepository.createUser(signupRequest)))
        } catch (e: Exception) {
            Log.d(CreateUserUseCase::class.simpleName, "createUser error: $e")
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}