package com.sielehub.treasuremart.domain.use_case.cart

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.local.database.StoreDao
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class AddProductToCartUseCaseTest {

    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var cartRepository: CartRepository
    private lateinit var storeDao: StoreDao
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase
    private lateinit var getCartUseCase: GetCartUseCase

    @Before
    fun setUp() {
        dataStoreManager = mockk()
        storeDao = mockk()
        cartRepository = mockk()
        getCartUseCase = GetCartUseCase(cartRepository)
        addProductToCartUseCase = AddProductToCartUseCase(cartRepository, dataStoreManager)
    }

    @Test
    fun `adding product to empty cart should create new cart with product`() = runTest {
        val userId = 1
        val cartProduct = CartProduct(
            productId = 1,
            quantity = 1
        )
        val expectedCart = Cart(
            id = 0,
            userId = userId,
            products = listOf(cartProduct),
            date = LocalDate.now().toString()
        )

        every { dataStoreManager.currentUserId } returns flowOf(userId)
        coEvery { cartRepository.getCart() } returns flowOf(null)
        coEvery { cartRepository.updateCart(expectedCart) } returns 1
        coEvery { cartRepository.getCart() } returns flowOf(expectedCart)

        val result = addProductToCartUseCase(cartProduct).last()
        val updatedCart = getCartUseCase().last()
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(expectedCart).isEqualTo(updatedCart.data)
    }

    @Test
    fun `adding product already exists in cart should increment quantity`() = runTest {
        val existingProduct = CartProduct(
            productId = 1,
            quantity = 1
        )
        val existingCart = Cart(
            id = 0,
            userId = 1,
            products = listOf(existingProduct),
            date = LocalDate.now().toString()
        )
        val updatedProduct = existingProduct.copy(quantity = 2)
        val expectedCart = existingCart.copy(products = listOf(updatedProduct))

        coEvery { cartRepository.getCart() } returns flowOf(existingCart)
        coEvery { cartRepository.updateCart(expectedCart) } returns 1
        coEvery { cartRepository.getCart() } returns flowOf(expectedCart)
        val addProductResult = addProductToCartUseCase(existingProduct).last()
        val updatedCartResult = getCartUseCase().last()

        assertThat(addProductResult).isInstanceOf(Resource.Success::class.java)
        assertThat(expectedCart.products[0].quantity).isEqualTo(updatedCartResult.data?.products[0]?.quantity)
    }

    @Test
    fun `adding product to cart when error occurs should return error resource`() = runTest {
        val cartProduct = CartProduct(
            productId = 1,
            quantity = 1
        )
        val errorMessage = "Database error"
        coEvery { cartRepository.getCart() } throws RuntimeException(errorMessage)

        val result = addProductToCartUseCase(cartProduct).last()
        assertThat(errorMessage).isEqualTo(result.message)
    }
} 