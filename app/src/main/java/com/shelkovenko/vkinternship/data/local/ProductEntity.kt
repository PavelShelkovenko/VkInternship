package com.shelkovenko.vkinternship.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ProductDatabase.PRODUCTS_TABLE_NAME)
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
    val rating: Double,
    val category: String,
    val images: String
)
