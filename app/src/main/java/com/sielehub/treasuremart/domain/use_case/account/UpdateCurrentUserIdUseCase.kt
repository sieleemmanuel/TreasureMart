package com.sielehub.treasuremart.domain.use_case.account

import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.domain.model.LoginRequest

class UpdateCurrentUserIdUseCase(
    private val dataStoreManager: DataStoreManager,
    private val authRepositoryImp: AuthRepositoryImpl
) {

    suspend operator fun invoke(loginRequest: LoginRequest) {
        val users = authRepositoryImp.getUsers()
        val currentUser = users.find {
            it.username == loginRequest.username && it.password == loginRequest.password
        }
        currentUser?.id?.let {
            dataStoreManager.setCurrentUserId(it)
        }
    }
}