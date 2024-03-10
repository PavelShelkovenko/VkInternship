package com.shelkovenko.vkinternship.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): ProductEntity?
    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE title LIKE '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<ProductEntity>
}