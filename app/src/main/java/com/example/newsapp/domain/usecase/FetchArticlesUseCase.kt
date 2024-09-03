package com.example.newsapp.domain.usecase

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.newsapp.common.Constants
import com.example.newsapp.common.Resource
import com.example.newsapp.data.model.Article
import com.example.newsapp.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val connectivityManager: ConnectivityManager
)  {
    operator fun invoke( category: Constants.CATEGORY): Flow<Resource<List<Article>>>{
        return if(isInternetAvailable()){

            articlesRepository.fetchArticles(category.value, Constants.COUNTRY_CODE)
        }else{
            articlesRepository.getCachedArticles(category.value)
        }
    }
    private fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}