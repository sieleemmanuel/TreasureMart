package com.sielehub.treasuremart.domain.use_case.orders

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetOrderUseCase(
    private val orderRepository: OrderRepository,
) {

    operator fun invoke(orderId: Long): Flow<Resource<Order?>> = flow {
        try {
            emit(Resource.Loading())
            val orders = orderRepository.getOrder(orderId).first()
            emit(Resource.Success(data = orders))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}