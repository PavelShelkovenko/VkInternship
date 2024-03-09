package com.shelkovenko.vkinternship.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelkovenko.vkinternship.domain.ProductsRepository
import com.shelkovenko.vkinternship.domain.models.Product
import com.shelkovenko.vkinternship.presentation.screens.products_list.ProductsListScreenState
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

    fun getProductDetails(productId: Int): Product? {
        return _state.value.productsList.find { it.id == productId }
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
                _state.update { it.copy(
                    isLoading = true,
                    isError = false,
                    errorMessage = null,
                    loadMoreError = null
                ) }
            }
            productsRepository.getProducts(skip = skip)
                .onSuccess { products ->
                    _state.update {
                        val newProductsList = it.productsList + products
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