package com.sielehub.treasuremart.domain.use_case.get_products

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.remote.dto.toProduct
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.repository.ProductRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsUseCase(private val productRepository: ProductRepository) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val products = productRepository.getProducts().map { it.toProduct() }
            emit(Resource.Success(data = products))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?: "Un unknown error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

}