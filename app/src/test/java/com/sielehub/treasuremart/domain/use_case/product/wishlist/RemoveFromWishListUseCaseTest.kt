package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.WishRepository
import com.sielehub.treasuremart.domain.use_case.wishlist.RemoveFromWishListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveFromWishListUseCaseTest {
    private lateinit var removeFromWishListUseCase: RemoveFromWishListUseCase
    val wishRepository: WishRepository = mockk()

    @Before
    fun setUp() {
        removeFromWishListUseCase = RemoveFromWishListUseCase(wishRepository)
    }

    @Test
    fun `Successful removal from wishlist should return Success`() = runTest {
        val productId = 1
        coEvery { wishRepository.removeFromWishlist(productId) } returns Unit
        val result = removeFromWishListUseCase(productId).toList()
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
    }

}