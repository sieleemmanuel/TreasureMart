package com.sielehub.treasuremart.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(Cart::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Query("SELECT * FROM cart_table")
    suspend fun getCarts(): List<Cart>

    @Query("SELECT * FROM cart_table WHERE userId = :userId")
    fun getCart(userId: Int): Flow<Cart?>

    @Query("UPDATE cart_table SET products = :products WHERE id = :cartId")
    suspend fun updateCart(cartId: Int, products: List<CartProduct>): Int

    @Query("SELECT EXISTS(SELECT * FROM cart_table WHERE id = :id)")
    suspend fun checkIsInCart(id: Int): Boolean

    @Query("DELETE FROM cart_table WHERE id = :id")
    suspend fun removeFromCart(id: Int)


}