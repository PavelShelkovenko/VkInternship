package com.shelkovenko.vkinternship.domain.models

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
    val rating: Double,
    val category: String,
    val images: List<String>
)