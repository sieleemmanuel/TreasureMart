package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.domain.repository.WishRepository
import com.sielehub.treasuremart.fakeProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetWishListUseCaseTest {
    private lateinit var getWishListUseCase: GetWishListUseCase
    val wishRepository: WishRepository = mockk()


    @Before
    fun setUp() {
        getWishListUseCase = GetWishListUseCase(wishRepository)
    }

    @Test
    fun `invoking getWishListUseCase should return a list of existing WishList`() = runTest {
        val expectedList = fakeProducts.take(5).map { WishProduct(it.id, it) }
        coEvery { wishRepository.getWishlist() } returns expectedList
        val result = getWishListUseCase().toList()
        assertThat(result[1].data).isEqualTo(expectedList)
    }
}