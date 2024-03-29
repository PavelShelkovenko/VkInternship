package com.shelkovenko.vkinternship.presentation

import androidx.compose.runtime.Composable
import com.shelkovenko.vkinternship.navigation.AppNavGraph
import com.shelkovenko.vkinternship.navigation.Screen
import com.shelkovenko.vkinternship.navigation.rememberNavigationState
import com.shelkovenko.vkinternship.presentation.screens.products_details.ProductsDetailsScreen
import com.shelkovenko.vkinternship.presentation.screens.products_list.ProductsListScreen
import com.shelkovenko.vkinternship.presentation.screens.search.SearchProductsScreen

@Composable
fun RootScreen() {

    val navigationState = rememberNavigationState()
    AppNavGraph(
        navHostController = navigationState.navHostController,
        productsDetailsScreenContent = { productId ->
            ProductsDetailsScreen(
                productId = productId,
                onBackPressed = { navigationState.navigateBack() }
            )
        },
        productsListScreenContent = {
            ProductsListScreen(
                onNavigateToSearchProducts = {
                    navigationState.navigateTo(Screen.ROUTE_SEARCH_PRODUCTS_SCREEN)
                },
                onNavigateToProductDetails = { productId ->
                    navigationState.navigateToProductDetailsScreen(id = productId)
                }
            )
        },
        searchProductsScreenContent = {
            SearchProductsScreen(
                onBackPressed = { navigationState.navigateBack() },
                onNavigateToProductDetails = { productId ->
                    navigationState.navigateToProductDetailsScreen(id = productId)
                }
            )
        }
    )
}




