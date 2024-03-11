package com.shelkovenko.vkinternship.presentation.screens.products_list

import com.shelkovenko.vkinternship.domain.models.Product
data class ProductsListScreenState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val productsList: List<Product> = emptyList(),
    val isLoadingNextProducts: Boolean = false,
    val loadMoreError: String? = null
)