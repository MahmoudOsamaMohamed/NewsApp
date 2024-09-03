package com.example.newsapp.data.model

data class ArticlesDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)