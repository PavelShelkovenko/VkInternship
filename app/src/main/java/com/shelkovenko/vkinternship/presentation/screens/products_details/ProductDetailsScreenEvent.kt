package com.shelkovenko.vkinternship.presentation.screens.products_details

sealed class ProductDetailsScreenEvent {
    data class GetProductDetails(
        val productId: Int
    ): ProductDetailsScreenEvent()
}