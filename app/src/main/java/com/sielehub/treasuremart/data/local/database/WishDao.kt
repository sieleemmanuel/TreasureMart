package com.sielehub.treasuremart.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sielehub.treasuremart.domain.model.WishProduct

@Dao
interface WishDao {
    @Insert(WishProduct::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishProduct(wishProduct: WishProduct)

    @Query("SELECT * FROM wishes_table")
    suspend fun getWishList(): List<WishProduct>

    @Query("SELECT EXISTS(SELECT * FROM wishes_table WHERE id = :id)")
    suspend fun checkIsWish(id: Int): Boolean

    @Query("DELETE FROM wishes_table WHERE id = :id")
    suspend fun removeFromWishList(id: Int)


}