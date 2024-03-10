package com.shelkovenko.vkinternship.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelkovenko.vkinternship.R
import com.shelkovenko.vkinternship.presentation.components.BackArrowButton
import com.shelkovenko.vkinternship.presentation.components.ErrorScreen
import com.shelkovenko.vkinternship.presentation.components.ProductsList

@Composable
fun SearchProductsScreen(
    onBackPressed: () -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
) {

    val viewModel = hiltViewModel<SearchProductViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    SearchProductsContent(
        viewModel = viewModel,
        state = state,
        onBackPressed = onBackPressed,
        onKeywordChanged = { newKeyword ->
            viewModel.onEvent(SearchProductsEvent.ChangeKeyword(newKeyword))
        },
        onNavigateToProductDetails = onNavigateToProductDetails
    )
}

@Composable
fun SearchProductsContent(
    viewModel: SearchProductViewModel,
    state: State<SearchProductsScreenState>,
    onBackPressed: () -> Unit,
    onKeywordChanged: (String) -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
) {
    val localState = state.value

    Column(
        modifier = Modifier
            .padding(vertical = 28.dp, horizontal = 12.dp)
    ) {
        SearchProductsHeader(
            keyword = localState.keyword,
            onBackPressed = { onBackPressed() },
            onKeywordChanged = { newKeyword -> onKeywordChanged(newKeyword) },
        )
        when {
            localState.isInitial -> {}
            localState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            localState.isEmptySearch -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(id = R.string.not_found),
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.padding(12.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
            localState.isError -> {
                ErrorScreen {
                    viewModel.onEvent(event = SearchProductsEvent.Repeat)
                }
            }

            else -> {
                ProductsList(
                    productsList = localState.products,
                    contentPadding = PaddingValues(top = 10.dp, bottom = 20.dp),
                    onProductClick = { productId ->
                        onNavigateToProductDetails(productId)
                    },
                    isLoadingNextProducts = false,
                    onShouldLoadMore = {}
                )
            }
        }
    }
}


@Composable
fun SearchProductsHeader(
    keyword: String,
    onBackPressed: () -> Unit,
    onKeywordChanged: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackArrowButton(
            modifier = Modifier,
            onBackPressed = { onBackPressed() }
        )
        TextField(
            value = keyword,
            onValueChange = { onKeywordChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_products),
                    style = MaterialTheme.typography.displayMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                unfocusedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
            trailingIcon = {
                if (keyword != "") {
                    IconButton(onClick = { onKeywordChanged("") }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}