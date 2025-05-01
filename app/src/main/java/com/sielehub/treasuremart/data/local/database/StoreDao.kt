package com.sielehub.treasuremart.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.WishProduct

@Dao
interface StoreDao {
    @Query("SELECT * FROM products_table")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products_table WHERE id = :id")
    fun getProductById(id: Int): Product

    @Insert(WishProduct::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertWishProduct(wishProduct: WishProduct)

    @Query("SELECT * FROM wishes_table")
    fun getWishList(): List<WishProduct>

    @Query("DELETE FROM wishes_table WHERE id = :id")
    fun removeFromWishList(id: Int)

}