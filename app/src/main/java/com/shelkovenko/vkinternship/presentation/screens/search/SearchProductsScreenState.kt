package com.shelkovenko.vkinternship.presentation.screens.search

import com.shelkovenko.vkinternship.domain.models.Product

data class SearchProductsScreenState(
    val keyword: String = "",
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isInitial: Boolean = true,
    val isEmptySearch: Boolean = false
)
