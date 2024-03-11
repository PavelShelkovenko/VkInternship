package com.shelkovenko.vkinternship.presentation.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shelkovenko.vkinternship.domain.models.Product

@Composable
fun ProductsList(
    productsList: List<Product>,
    modifier: Modifier = Modifier,
    isLoadingNextProducts: Boolean,
    onProductClick: (Int) -> Unit,
    contentPadding: PaddingValues,
    loadMoreError: String? = null,
    onShouldLoadMore: () -> Unit,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    val shouldLoadMore = remember(productsList.size) {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex + 7 == productsList.size
        }
    }
    LaunchedEffect(key1 = shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onShouldLoadMore()
        }
    }
    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = contentPadding
    ) {
        items(
            productsList,
            key = { product -> product.id }
        ) { product ->
            ProductCard(
                product = product,
                onProductClick = onProductClick
            )
        }
        if (isLoadingNextProducts) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (loadMoreError != null) {
            item {
                Button(
                    onClick = { onShouldLoadMore() }
                ) {
                    Text(text = "Repeat")
                }
            }
        }
    }
}