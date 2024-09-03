package com.example.newsapp.presentation.viewstates

import com.example.newsapp.common.Constants
import com.example.newsapp.data.model.Article

data class HomeNewsState(
    val categories: List<Constants.CATEGORY> = Constants.CATEGORY.entries,
    val selectedCategory: Constants.CATEGORY = Constants.CATEGORY.GENERAL,
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

)