package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.remote.StoreService
import com.sielehub.treasuremart.data.remote.dto.CategoryDto
import com.sielehub.treasuremart.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val storeService: StoreService ):
CategoryRepository{

    override suspend fun getCategories(): List<CategoryDto> {
        return storeService.getCategories()
    }

    override suspend fun getCategory(categoryId: Int): CategoryDto {
        return storeService.getCategory(categoryId)
    }
}