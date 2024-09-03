package com.example.newsapp.data.repository

import android.util.Log
import com.example.newsapp.common.Resource
import com.example.newsapp.common.editCategory
import com.example.newsapp.domain.repository.ArticlesRepository
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.model.Article

import com.example.newsapp.data.remote.ArticlesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val articleApi : ArticlesApi,
    private val articleDao: ArticleDao
): ArticlesRepository {

    override fun fetchArticles(
        category: String,
        countryCode: String
    ): Flow<Resource<List<Article>>> {
        Log.d("ArticlesRepository", "i'm here")
        return flow {

            emit(Resource.Loading())
            val remoteArticles = articleApi.getTopHeadlines(category, countryCode).articles
            Log.d("ArticlesRepository", remoteArticles.toString())
            articleDao.deleteAllArticles(category)
            remoteArticles.forEach { article ->
                article.category = editCategory(category)
            }
            articleDao.insertArticles(remoteArticles)
            val cachedArticles = articleDao.getAllArticles(category).first()
            emit(Resource.Success(cachedArticles))
        }.catch {
            Log.d("ArticlesRepositoryError", it.message.toString())
            emit(Resource.Error(it.message ?: "An unexpected error occurred"))
        }
    }
    override fun getCachedArticles(
        category: String
    ): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        val cachedArticles = articleDao.getAllArticles(category).first()

        emit(Resource.Success(cachedArticles))
    }.catch {

        emit(Resource.Error(it.message ?: "An unexpected error occurred"))
    }


}