package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun createOrder(order: Order): Boolean

    suspend fun updateOrder(order: Order): Boolean

    fun getOrders(): Flow<List<Order>>

    fun getOrder(orderId: Long): Flow<Order?>

    suspend fun deleteOrder(orderId: Long): Boolean

}