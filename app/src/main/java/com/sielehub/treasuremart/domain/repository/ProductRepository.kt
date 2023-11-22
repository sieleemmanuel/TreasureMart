package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.data.remote.dto.ProductDto

interface ProductRepository {

    suspend fun getProducts(): List<ProductDto>

    suspend fun getProduct(id: Int): ProductDto

}