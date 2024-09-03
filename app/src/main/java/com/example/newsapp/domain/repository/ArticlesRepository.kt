package com.example.newsapp.domain.repository

import com.example.newsapp.common.Resource
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow


interface ArticlesRepository {
    fun fetchArticles(category: String, countryCode: String): Flow<Resource<List<Article>>>
    fun getCachedArticles(category: String):Flow<Resource<List<Article>>>

}