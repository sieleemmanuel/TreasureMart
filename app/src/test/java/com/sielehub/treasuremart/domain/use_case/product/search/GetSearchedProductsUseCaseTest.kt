package com.sielehub.treasuremart.domain.use_case.product.search

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.use_case.search.GetSearchedProductsUseCase
import com.sielehub.treasuremart.fakeProducts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetSearchedProductsUseCaseTest {
    val storeRepository = mockk<StoreRepositoryImpl>()
    val getSearchedProductsUseCase = GetSearchedProductsUseCase(storeRepository)

    @Test
    fun `invoke should return list of searched products`() = runTest {
        val query = "jacket"
        val expectedProductsResult = fakeProducts.filter {
            it.title.contains(
                query,
                ignoreCase = true
            ) || it.description.contains(query, ignoreCase = true)
        }
        coEvery { storeRepository.getSearchedProducts(query) } returns expectedProductsResult
        val searchedResult = getSearchedProductsUseCase(query).toList()
        assertThat(searchedResult[1].data?.size).isEqualTo(expectedProductsResult.size)
    }


}