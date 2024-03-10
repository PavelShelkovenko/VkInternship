package com.shelkovenko.vkinternship.presentation.screens.products_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shelkovenko.vkinternship.R
import com.shelkovenko.vkinternship.domain.models.Product
import com.shelkovenko.vkinternship.presentation.components.BackArrowButton

@Composable
fun ProductsDetailsScreen(
    productId: Int,
    onBackPressed: () -> Unit
) {
    val viewModel = hiltViewModel<ProductDetailsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(
            ProductDetailsScreenEvent.LoadProduct(productId = productId)
        )
    }

    ProductDetailsContent(
        state = state,
        onBackPressed = onBackPressed,
    )
}

@Composable
fun ProductDetailsContent(
    state: State<ProductDetailsScreenState>,
    onBackPressed: () -> Unit,
) {
    val localState = state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (localState) {
            is ProductDetailsScreenState.Content -> {
                LazyColumn {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AsyncImage(
                                model = localState.product.thumbnail,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.ic_empty_image_placeholder),
                                error = painterResource(R.drawable.ic_empty_image_placeholder),
                            )
                        }
                    }
                    item {
                        ProductDetailsDescription(
                            modifier = Modifier,
                            productDetails = localState.product
                        )
                    }
                }

            }

            ProductDetailsScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        BackArrowButton(
            modifier = Modifier.padding(vertical = 28.dp, horizontal = 12.dp),
            onBackPressed = { onBackPressed() }
        )
    }
}

@Composable
fun ProductDetailsDescription(
    modifier: Modifier = Modifier,
    productDetails: Product
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        Text(
            text = productDetails.title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = productDetails.description,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${stringResource(id = R.string.category)}: ${productDetails.category}",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${stringResource(id = R.string.price)}: ${productDetails.price}$",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${stringResource(id = R.string.rating)}: ${productDetails.rating} / 5",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(id = R.string.other_images),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productDetails.images) { imageUrl ->
                Card(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            ambientColor = Color.DarkGray,
                            spotColor = Color.DarkGray,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .width(160.dp)
                            .height(160.dp),
                        contentScale = ContentScale.FillBounds,
                        placeholder = painterResource(R.drawable.ic_empty_image_placeholder),
                        error = painterResource(R.drawable.ic_empty_image_placeholder),
                    )
                }
            }
        }
    }
}