package com.shelkovenko.vkinternship.presentation.screens.products_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelkovenko.vkinternship.domain.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ProductsListScreenState> =
        MutableStateFlow((ProductsListScreenState()))
    val state = _state.asStateFlow()

    init {
        downloadProducts()
    }

    fun loadMore() {
        val skip = state.value.productsList.size
        if (skip <= 80) {
            _state.update {
                it.copy(
                    isLoadingNextProducts = true,
                    isLoading = false,
                    isError = false,
                    errorMessage = null,
                    loadMoreError = null
                )
            }
            downloadProducts(skip)
        }
    }

    fun downloadProducts(skip: Int = 0) {
        viewModelScope.launch {
            if (skip == 0) {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isError = false,
                        errorMessage = null,
                        loadMoreError = null
                    )
                }
            }
            productsRepository.getProducts(skip = skip)
                .onSuccess { products ->
                    if (products != _state.value.productsList) {
                        val newProductsList = _state.value.productsList + products
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                errorMessage = null,
                                loadMoreError = null,
                                productsList = newProductsList,
                                isLoadingNextProducts = false
                            )
                        }
                    }
                }
                .onFailure { error ->
                    _state.update {
                        if (it.productsList.isNotEmpty()) {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                errorMessage = null,
                                loadMoreError = error.message,
                                isLoadingNextProducts = false
                            )
                        } else {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = error.message,
                                loadMoreError = null,
                                isLoadingNextProducts = false
                            )
                        }
                    }
                }
        }
    }
}