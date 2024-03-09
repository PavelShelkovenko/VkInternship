package com.shelkovenko.vkinternship.data

import com.shelkovenko.vkinternship.data.models.ProductDto
import com.shelkovenko.vkinternship.data.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): ProductsResponse

    @GET("products/{id}")
    fun getProductById(
        @Path("id") id: Int,
    ): ProductDto

    @GET("products/search")
    fun searchProduct(
        @Query("q") keyword: String
    ): ProductsResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}