package com.shelkovenko.vkinternship.presentation.screens.products_details

import com.shelkovenko.vkinternship.domain.models.Product

sealed class ProductDetailsScreenState {
    data object Loading: ProductDetailsScreenState()
    data class Content(
        val product: Product
    ): ProductDetailsScreenState()
}


