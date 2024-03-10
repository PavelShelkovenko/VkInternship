package com.shelkovenko.vkinternship.domain

import com.shelkovenko.vkinternship.domain.models.Product

interface ProductsRepository {
    suspend fun getProducts(skip: Int): Result<List<Product>>
    suspend fun getProductById(id: Int): Result<Product>
    suspend fun searchProduct(keyword: String): Result<List<Product>>
}