package com.shelkovenko.vkinternship.presentation.screens.products_details

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
class ProductDetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val _state: MutableStateFlow<ProductDetailsScreenState> =
        MutableStateFlow(ProductDetailsScreenState.Loading)
    val state = _state.asStateFlow()


    fun onEvent(event: ProductDetailsScreenEvent) {
        when(event) {
            is ProductDetailsScreenEvent.LoadProduct -> {
                setProductDetails(productId = event.productId)
            }
            is ProductDetailsScreenEvent.Repeat -> {
                setProductDetails(productId = event.productId)
            }
        }
    }


    private fun setProductDetails(productId: Int) {
        viewModelScope.launch {
            val result = productsRepository.getProductById(id = productId)
            result.onSuccess { product ->
                _state.update {
                    ProductDetailsScreenState.Content(product = product)
                }
            }.onFailure {
                _state.update {
                    ProductDetailsScreenState.Loading
                }
            }
        }

    }
}