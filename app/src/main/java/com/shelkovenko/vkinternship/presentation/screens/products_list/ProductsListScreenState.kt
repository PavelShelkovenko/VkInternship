package com.shelkovenko.vkinternship.presentation.screens.products_list

import com.shelkovenko.vkinternship.domain.models.Product

//sealed class ProductsListScreenState {
//    data object Loading: ProductsListScreenState()
//    data class Error(val errorMessage: String): ProductsListScreenState()
//    data class Content(
//        val productsList: List<Product>
//    ): ProductsListScreenState()
//}

data class ProductsListScreenState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val productsList: List<Product> = emptyList(),
    val isLoadingNextProducts: Boolean = false,
    val loadMoreError: String? = null
)