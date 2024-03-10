package com.shelkovenko.vkinternship.data

import com.shelkovenko.vkinternship.data.local.ProductEntity
import com.shelkovenko.vkinternship.data.remote.ProductDto
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

fun ProductDto.toProductEntity(): ProductEntity =
    ProductEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        thumbnail = this.thumbnail,
        rating = this.rating,
        category = this.category,
        images = this.images.joinToString(",")
    )

fun ProductEntity.toProduct(): Product =
    Product(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        thumbnail = this.thumbnail,
        rating = this.rating,
        category = this.category,
        images = this.images.split(",")
    )
