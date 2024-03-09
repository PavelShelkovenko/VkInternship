package com.shelkovenko.vkinternship.presentation.screens.products_details

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.shelkovenko.vkinternship.presentation.screens.ProductsViewModel

@Composable
fun ProductsDetailsScreen(
    productId: Int,
    onBackPressed: () -> Unit
) {
    val viewModel = hiltViewModel<ProductsViewModel>()
    Log.d("TAG", "$viewModel")
}