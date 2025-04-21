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

class GetProductsByCategoryUseCaseTest {
    private val storeRepositoryImpl: StoreRepositoryImpl = mockk()
    private lateinit var getProductsByCategoryUseCase: GetProductsByCategoryUseCase

    @Before
    fun setUp() {
        getProductsByCategoryUseCase = GetProductsByCategoryUseCase(storeRepositoryImpl)
    }

    @Test
    fun `invoke should return a list of products by category`() = runTest {
        val categoryElectronic = "electronics"
        val expectedProducts = fakeProducts.filter { it.category == categoryElectronic }
        coEvery { storeRepositoryImpl.getProductsByCategory(categoryElectronic) } returns expectedProducts
        val categoryProductResult = getProductsByCategoryUseCase(categoryElectronic).toList()
        val allOfSameCategory =
            categoryProductResult[1].data?.groupBy { it.category }?.toList()?.size == 1
        assertThat(allOfSameCategory).isTrue()
        assertThat(categoryProductResult[1].data?.groupBy { it.category }
            ?.toList()?.first()?.first).isEqualTo(categoryElectronic)
    }
}