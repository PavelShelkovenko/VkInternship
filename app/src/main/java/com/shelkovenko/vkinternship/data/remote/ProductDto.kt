package com.shelkovenko.vkinternship.data.remote

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("category")
    val category: String,
    @SerializedName("images")
    val images: List<String>
)