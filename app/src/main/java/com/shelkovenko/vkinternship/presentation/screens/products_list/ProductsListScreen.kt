package com.shelkovenko.vkinternship.presentation.screens.products_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelkovenko.vkinternship.presentation.components.ErrorScreen
import com.shelkovenko.vkinternship.presentation.components.ProductsList
import com.shelkovenko.vkinternship.presentation.components.ShimmersProductList
import com.shelkovenko.vkinternship.presentation.components.TopBar

@Composable
fun ProductsListScreen(
    onNavigateToSearchProducts: () -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
) {

    val viewModel = hiltViewModel<ProductsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    ProductsListContent(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onNavigateToSearchProducts,
        onNavigateToProductDetails,
        onRetryDownloadProducts = viewModel::downloadProducts,
        onShouldLoadMore = viewModel::loadMore
    )
}

@Composable
fun ProductsListContent(
    state: State<ProductsListScreenState>,
    modifier: Modifier = Modifier,
    onNavigateToSearchProducts: () -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
    onRetryDownloadProducts: () -> Unit,
    onShouldLoadMore: () -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Column {

            TopBar(title = "Popular products") {
                onNavigateToSearchProducts()
            }

            if (state.value.isLoading) {
                ShimmersProductList(
                    cardCount = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            } else {
                if (state.value.isError) {
                    ErrorScreen {
                        onRetryDownloadProducts()
                    }
                }
                else {
                    ProductsList(
                        productsList = state.value.productsList,
                        loadMoreError = state.value.loadMoreError,
                        contentPadding = PaddingValues(top = 10.dp, bottom = 70.dp),
                        onProductClick = onNavigateToProductDetails,
                        onShouldLoadMore = onShouldLoadMore,
                        isLoadingNextProducts = state.value.isLoadingNextProducts
                    )
                }
            }
        }
    }
}