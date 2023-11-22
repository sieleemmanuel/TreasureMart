package com.sielehub.treasuremart.domain.use_case.get_categories

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.remote.dto.toCategory
import com.sielehub.treasuremart.domain.model.Category
import com.sielehub.treasuremart.domain.repository.CategoryRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(categoryId: Int): Flow<Resource<Category>> = flow {
        try {
            emit(Resource.Loading())
            val category = categoryRepository.getCategory(categoryId).toCategory()
            emit(Resource.Success(data = category))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?: "Un unknown error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

}