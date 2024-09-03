package com.example.newsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.common.Constants
import com.example.newsapp.common.Resource
import com.example.newsapp.data.model.Article
import com.example.newsapp.domain.usecase.FetchArticlesUseCase
import com.example.newsapp.presentation.intent.HomeNewsIntent
import com.example.newsapp.presentation.viewstates.HomeNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNewsViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeNewsState())
    val state: StateFlow<HomeNewsState> = _state

    init {

        handleIntent(HomeNewsIntent.SelectCategory(Constants.CATEGORY.entries.first()))

    }

    fun handleIntent(intent: HomeNewsIntent) {
        when (intent) {

            is HomeNewsIntent.SelectCategory -> {

                _state.value = _state.value.copy(selectedCategory = intent.category, isLoading = true)
                fetchArticles()
            }

        }
    }

    private fun fetchArticles() {

        viewModelScope.launch {


            fetchArticlesUseCase( _state.value.selectedCategory).collect {result ->

                when (result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(articles = result.data?: emptyList(), isLoading = false)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message)

                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }

        }
    }


}
