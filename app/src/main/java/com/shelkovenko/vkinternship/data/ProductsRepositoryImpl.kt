package com.shelkovenko.vkinternship.data

import com.shelkovenko.vkinternship.data.local.ProductDao
import com.shelkovenko.vkinternship.data.remote.ApiService
import com.shelkovenko.vkinternship.domain.ProductsRepository
import com.shelkovenko.vkinternship.domain.models.Product
import retrofit2.HttpException

class ProductsRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductsRepository {
    override suspend fun getProducts(skip: Int): Result<List<Product>> {
        return try {
            getProductsFromNetwork(skip)
        } catch (ex: HttpException) {
            getProductsFromCache()
        } catch (ex: Exception) {
            getProductsFromCache()
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            getProductByIdFromCache(id = id)
        } catch (ex: IllegalStateException) {
            getProductByIdFromNetwork(id = id)
        } catch (ex: Exception) {
            Result.failure(IllegalStateException(ex.message))
        }
    }

    private suspend fun getProductByIdFromNetwork(id: Int): Result<Product> {
        val response = apiService.getProductById(id = id)
        val result = response.toProduct()
        return Result.success(result)
    }
    private suspend fun getProductByIdFromCache(id: Int): Result<Product> {
        val cachedProduct = productDao.getProductById(productId = id)
            ?: throw IllegalStateException("Empty cache")
        return Result.success(cachedProduct.toProduct())
    }

    override suspend fun searchProduct(keyword: String): Result<List<Product>> {
        return try {
            searchProductsFromNetwork(keyword)
        } catch (ex: HttpException) {
            searchProductsFromCache(query = keyword)
        } catch (ex: Exception) {
            searchProductsFromCache(query = keyword)
        }
    }
    private suspend fun searchProductsFromNetwork(keyword: String): Result<List<Product>> {
        val response = apiService.searchProduct(keyword = keyword)
        val resultList = response.products.map { productDto ->
            productDto.toProduct()
        }
        return Result.success(resultList)
    }

    private suspend fun searchProductsFromCache(query: String): Result<List<Product>> {
        val cachedProductsForSearch = productDao.searchProducts(query = query)
        if (cachedProductsForSearch.isEmpty()) {
            return Result.failure(IllegalStateException("Empty cache"))
        }
        val result = cachedProductsForSearch.map { productEntity ->
            productEntity.toProduct()
        }
        return Result.success(result)
    }


    private suspend fun getProductsFromNetwork(skip: Int): Result<List<Product>> {
        val response = apiService.getProducts(skip = skip)
        val resultList = response.products.map { productDto ->
            productDto.toProduct()
        }
        response.products.map { productDto ->
            productDto.toProductEntity()
        }.also {
            productDao.putProducts(it)
        }
        return Result.success(resultList)
    }

    private suspend fun getProductsFromCache(): Result<List<Product>> {
        val cachedProducts = productDao.getProducts()
        if (cachedProducts.isEmpty()) {
            return Result.failure(IllegalStateException("Empty cache"))
        }
        val resultList = cachedProducts.map { productEntity ->
            productEntity.toProduct()
        }
        return Result.success(resultList)
    }

}