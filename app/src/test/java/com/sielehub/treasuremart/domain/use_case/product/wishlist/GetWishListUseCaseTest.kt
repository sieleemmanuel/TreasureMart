package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetWishListUseCaseTest {
    private lateinit var getWishListUseCase: GetWishListUseCase
    val storeRepositoryImpl: StoreRepositoryImpl = mockk()


    @Before
    fun setUp() {
        getWishListUseCase = GetWishListUseCase(storeRepositoryImpl)
    }

    @Test
    fun `invoking getWishListUseCase should return a list of existing WishList`() = runTest {
        val expectedList = Constants.wishList
        coEvery { storeRepositoryImpl.getWishlist() } returns expectedList
        val result = getWishListUseCase().toList()
        assertThat(result[1].data).isEqualTo(Constants.products().take(5))
    }
}