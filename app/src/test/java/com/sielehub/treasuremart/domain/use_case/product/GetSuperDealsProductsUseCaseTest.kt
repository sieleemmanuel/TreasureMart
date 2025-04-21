package com.sielehub.treasuremart.domain.use_case.product

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.fakeProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSuperDealsProductsUseCaseTest {
    private lateinit var getSuperDealsProductsUseCase: GetSuperDealsProductsUseCase
    private val storeRepositoryImpl: StoreRepositoryImpl = mockk()

    @Before
    fun setUp() {
        getSuperDealsProductsUseCase = GetSuperDealsProductsUseCase(storeRepositoryImpl)
    }

    @Test
    fun `invoke should return a list of super deals products`() = runTest {
        val expectedProducts = fakeProducts.groupBy { it.category }.map { (category, products) ->
            products.take(2)
        }.flatten()
        coEvery { storeRepositoryImpl.getSuperDealsProducts() } returns expectedProducts
        val productsResult = getSuperDealsProductsUseCase().toList()
        assertThat(productsResult[1].data).isEqualTo(expectedProducts)
    }
}