package com.sielehub.treasuremart.domain.use_case.checkout

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.repository.OrderRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceOrderUseCase(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(order: Order): Flow<Resource<Order>> = flow {
        emit(Resource.Loading())
        delay(4000)
        try {
            val result = orderRepository.createOrder(order)
            if (result != null) {
                emit(Resource.Success(order))
            } else {
                emit(Resource.Error("Failed to place order"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }
}