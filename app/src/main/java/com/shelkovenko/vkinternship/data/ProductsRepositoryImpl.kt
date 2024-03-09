package com.shelkovenko.vkinternship.data

import com.shelkovenko.vkinternship.domain.ProductsRepository
import com.shelkovenko.vkinternship.domain.models.Product
import retrofit2.HttpException

class ProductsRepositoryImpl(
    private val apiService: ApiService,
): ProductsRepository {
    override suspend fun getProducts(skip: Int): Result<List<Product>> {
        return try {
            val response = apiService.getProducts(skip = skip)
            val resultList = response.products.map { productDto ->
                productDto.toProduct()
            }
            Result.success(resultList)
        } catch (ex: HttpException) {
            Result.failure(IllegalStateException(ex.message()))
        } catch (ex: Exception) {
            Result.failure(IllegalStateException(ex.message))
        }
    }

    override fun getProductById(id: Int): Result<Product> {
        TODO("Not yet implemented")
    }

    override fun searchProduct(keyword: String): Result<List<Product>> {
        TODO("Not yet implemented")
    }
}