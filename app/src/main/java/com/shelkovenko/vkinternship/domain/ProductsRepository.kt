package com.shelkovenko.vkinternship.domain

import com.shelkovenko.vkinternship.domain.models.Product

interface ProductsRepository {
    suspend fun getProducts(skip: Int): Result<List<Product>>

    fun getProductById(id: Int): Result<Product>
    fun searchProduct(keyword: String): Result<List<Product>>
}