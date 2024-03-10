package com.shelkovenko.vkinternship.presentation.screens.products_details

sealed class ProductDetailsScreenEvent {
    data class LoadProduct(val productId: Int): ProductDetailsScreenEvent()
    data class Repeat(val productId: Int) : ProductDetailsScreenEvent()
}