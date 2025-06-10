package com.sielehub.treasuremart.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Insert(Order::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Query("SELECT * FROM orders_table")
    fun getOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders_table WHERE orderId = :orderId")
    fun getOrder(orderId: Long): Flow<Order?>

    @Query("UPDATE orders_table SET orderItems = :products WHERE orderId = :orderId")
    suspend fun updateOrder(orderId: Long, products: List<CartProduct>): Int

    @Query("DELETE FROM orders_table WHERE orderId = :orderId")
    suspend fun removeOrder(orderId: Long): Int

}