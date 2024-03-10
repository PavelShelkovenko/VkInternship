package com.shelkovenko.vkinternship.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelkovenko.vkinternship.domain.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val _state: MutableStateFlow<SearchProductsScreenState> =
        MutableStateFlow((SearchProductsScreenState()))
    val state = _state.asStateFlow()


    fun onEvent(event: SearchProductsEvent) {
        when(event) {
            is SearchProductsEvent.ChangeKeyword -> {
                _state.update {it.copy(keyword = event.newKeyword) }
                searchProducts(event.newKeyword)
            }
            is SearchProductsEvent.Repeat -> {
                searchProducts(_state.value.keyword)
            }
        }
    }

    private var searchJob: Job? = null

    private fun searchProducts(newKeyword: String) {
        viewModelScope.launch {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(800)
                _state.update {
                    it.copy(
                        isInitial = false,
                        isLoading = true,
                        isError = false,
                        isEmptySearch = false
                    )
                }
                val result = productsRepository.searchProduct(keyword = newKeyword)
                result.onSuccess { products ->
                    if (products.isEmpty()) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isEmptySearch = true,
                                isError = false,
                                products = emptyList()
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                products = products,
                                isLoading = false,
                                isEmptySearch = false,
                                isError = false
                            )
                        }
                    }
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            isEmptySearch = false
                        )
                    }
                }
            }
        }
    }
}