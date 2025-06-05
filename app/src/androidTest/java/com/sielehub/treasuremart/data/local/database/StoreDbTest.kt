package com.sielehub.treasuremart.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.Rating
import com.sielehub.treasuremart.domain.model.WishProduct
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class StoreDbTest {
    private lateinit var storeDb: StoreDb
    private lateinit var storeDao: StoreDao
    private lateinit var wishDao: WishDao
    private lateinit var cartDao: CartDao

    val fakeProduct = Product(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        category = "men's clothing",
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        rating = Rating(rate = 3.9, count = 120)
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        storeDb = Room.inMemoryDatabaseBuilder(context, StoreDb::class.java).build()
        storeDao = storeDb.storeDao
        wishDao = storeDb.wishDao
        cartDao = storeDb.cartDao
    }

    @Test
    fun testAddToWishlist() = runBlocking {
        val wishProduct = WishProduct(fakeProduct.id, fakeProduct)
        wishDao.insertWishProduct(wishProduct)
        val isInserted = wishDao.checkIsWish(fakeProduct.id)
        assertThat(isInserted).isTrue()
    }

    @Test
    fun testRemoveFromWishlist() = runBlocking {
        val wishProduct = WishProduct(fakeProduct.id, fakeProduct)
        wishDao.insertWishProduct(wishProduct)
        wishDao.removeFromWishList(fakeProduct.id)
        val isInserted = wishDao.checkIsWish(fakeProduct.id)
        assertThat(isInserted).isFalse()
    }

    @Test
    fun testAllWishListProducts() = runTest {
        val wishList = listOf(WishProduct(fakeProduct.id, fakeProduct))
        wishList.forEach { wishDao.insertWishProduct(it) }
        val allProducts = wishDao.getWishList()
        assertThat(allProducts).isEqualTo(wishList)
    }

    @Test
    fun testAddToCart() = runTest {
        val cartProduct = CartProduct(fakeProduct.id, 1)
        val newCart = Cart(
            id = 1,
            date = Date().toString(),
            userId = 1,
            products = listOf(cartProduct)
        )
        cartDao.insertCart(newCart)
        val isInserted = cartDao.checkIsInCart(newCart.id)
        assertThat(isInserted).isTrue()
    }

    @Test
    fun testRemoveFromCart() = runTest {
        val cartProduct = CartProduct(fakeProduct.id, 1)
        val newCart = Cart(
            id = 1,
            date = Date().toString(),
            userId = 1,
            products = listOf(cartProduct)
        )
        cartDao.insertCart(newCart)
        cartDao.removeFromCart(newCart.id)
        val isInserted = cartDao.checkIsInCart(newCart.id)
        assertThat(isInserted).isFalse()
    }

    @After
    fun tearDown() {
        storeDb.close()
    }

}