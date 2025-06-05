package com.sielehub.treasuremart.domain.use_case.account

import android.util.Log
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.LoginRequest
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<Token?>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(authRepository.authenticateUser(loginRequest)))
        } catch (e: Exception) {
            Log.d(LoginUseCase::class.simpleName, "Login error: ${e.localizedMessage}")
            emit(
                Resource.Error(e.localizedMessage ?: "An unknown error occurred")
            )
        }
    }
}