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

class GetBestPickProductsUseCaseTest {
    private lateinit var getBestPickProductsUseCase: GetBestPickProductsUseCase
    private lateinit var storeRepositoryImpl: StoreRepositoryImpl

    @Before
    fun setUp() {
        storeRepositoryImpl = mockk()
        getBestPickProductsUseCase = GetBestPickProductsUseCase(storeRepositoryImpl)
    }

    @Test
    fun `invoke should return a list of best pick products`() = runTest {
        val bestPickProducts = fakeProducts.shuffled().take(5)
        coEvery { storeRepositoryImpl.getBestPickProducts() } returns bestPickProducts
        val productResult = getBestPickProductsUseCase().toList()
        assertThat(productResult[1].data).isEqualTo(bestPickProducts)
    }

}