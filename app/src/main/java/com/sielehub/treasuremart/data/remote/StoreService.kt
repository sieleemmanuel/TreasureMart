package com.sielehub.treasuremart.data.remote

import com.sielehub.treasuremart.data.remote.dto.CategoryDto
import com.sielehub.treasuremart.data.remote.dto.ProductDto

interface StoreService {

    suspend fun getProducts(): List<ProductDto>

    suspend fun getProduct(id: Int): ProductDto

    suspend fun getCategories(): List<CategoryDto>

    suspend fun getCategory(categoryId: Int): CategoryDto
}