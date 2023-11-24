package com.sielehub.treasuremart.domain.use_case.user

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateUserUseCase(private val storeRepository: StoreRepository) {

    suspend operator fun invoke(user: User): Resource<User?> = storeRepository.createUser(user)
}