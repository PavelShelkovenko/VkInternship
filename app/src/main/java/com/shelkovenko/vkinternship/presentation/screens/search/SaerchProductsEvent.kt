package com.shelkovenko.vkinternship.presentation.screens.search

sealed class SearchProductsEvent {
    data class ChangeKeyword(val newKeyword: String): SearchProductsEvent()
    data object Repeat: SearchProductsEvent()
}
