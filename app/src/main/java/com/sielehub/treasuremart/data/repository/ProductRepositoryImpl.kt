package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.remote.StoreService
import com.sielehub.treasuremart.data.remote.dto.ProductDto
import com.sielehub.treasuremart.domain.repository.ProductRepository

class ProductRepositoryImpl(private val storeService: StoreService ):
ProductRepository{
    override suspend fun getProducts(): List<ProductDto> {
        return storeService.getProducts()
    }

    override suspend fun getProduct(id: Int): ProductDto {
        return storeService.getProduct(id)
    }
}