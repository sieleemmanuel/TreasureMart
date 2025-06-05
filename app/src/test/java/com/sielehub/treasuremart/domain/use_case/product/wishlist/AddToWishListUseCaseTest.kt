package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.domain.repository.WishRepository
import com.sielehub.treasuremart.fakeProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddToWishListUseCaseTest {
    private lateinit var addToWishListUseCase: AddToWishListUseCase
    val wishRepository: WishRepository = mockk()

    @Before
    fun setUp() {
        addToWishListUseCase = AddToWishListUseCase(wishRepository)
    }

    @Test
    fun `Successful addition to wishlist should return Success`() = runTest {
        val product = fakeProducts.map { WishProduct(it.id, it) }.first()
        coEvery { wishRepository.addToWishlist(product) } returns Unit
        val result = addToWishListUseCase(product).toList()
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
    }

}