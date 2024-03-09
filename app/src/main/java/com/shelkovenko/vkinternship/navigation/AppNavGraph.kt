package com.shelkovenko.vkinternship.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    productsListScreenContent: @Composable () -> Unit,
    searchProductsScreenContent: @Composable () -> Unit,
    productsDetailsScreenContent: @Composable (Int) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ProductsListScreen.route
    ) {
        composable(route = Screen.ProductsListScreen.route) {
            productsListScreenContent()
        }
        composable(route = Screen.SearchProductsScreen.route) {
            searchProductsScreenContent()
        }
        composable(
            route = Screen.ProductDetailsScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_PRODUCT_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(Screen.KEY_PRODUCT_ID) ?: 0
            productsDetailsScreenContent(id)
        }
    }
}