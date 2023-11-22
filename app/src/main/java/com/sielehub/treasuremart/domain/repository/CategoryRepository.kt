package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.data.remote.dto.CategoryDto

interface CategoryRepository {

    suspend fun getCategories(): List<CategoryDto>

    suspend fun getCategory(categoryId: Int): CategoryDto

}