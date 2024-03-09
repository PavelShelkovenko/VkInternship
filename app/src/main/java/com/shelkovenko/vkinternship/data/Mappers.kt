package com.shelkovenko.vkinternship.data

import com.shelkovenko.vkinternship.data.models.ProductDto
import com.shelkovenko.vkinternship.domain.models.Product

fun ProductDto.toProduct(): Product =
    Product(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        thumbnail = this.thumbnail,
        rating = this.rating,
        category = this.category,
        images = this.images
    )
fun Product.toProductDto(): ProductDto =
    ProductDto(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        thumbnail = this.thumbnail,
        rating = this.rating,
        category = this.category,
        images = this.images
    )