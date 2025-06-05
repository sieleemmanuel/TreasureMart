package com.sielehub.treasuremart.data.local.database

import androidx.room.Dao
import androidx.room.Query
import com.sielehub.treasuremart.domain.model.Product

@Dao
interface StoreDao {
    @Query("SELECT * FROM products_table")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products_table WHERE id = :id")
    suspend fun getProductById(id: Int): Product

}