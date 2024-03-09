package com.shelkovenko.vkinternship.navigation

sealed class Screen(
    val route: String
) {
    data object ProductsListScreen: Screen(ROUTE_PRODUCTS_LIST_SCREEN)
    data object SearchProductsScreen: Screen(ROUTE_SEARCH_PRODUCTS_SCREEN)
    data object ProductDetailsScreen: Screen(ROUTE_PRODUCT_DETAILS_SCREEN) {
        private const val ROUTE_FOR_ARGS = "product_details"
        fun getRouteWithArgs(id: Int): String {
            return "$ROUTE_FOR_ARGS/$id"
        }
    }


    companion object {

        const val KEY_PRODUCT_ID = "product_id"

        const val ROUTE_PRODUCTS_LIST_SCREEN = "products_list"
        const val ROUTE_SEARCH_PRODUCTS_SCREEN = "search_products"
        const val ROUTE_PRODUCT_DETAILS_SCREEN = "product_details/{$KEY_PRODUCT_ID}"
    }
}