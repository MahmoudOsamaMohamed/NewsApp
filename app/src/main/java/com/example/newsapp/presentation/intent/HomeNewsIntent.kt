package com.example.newsapp.presentation.intent

import com.example.newsapp.common.Constants
import com.example.newsapp.data.model.Article

sealed class HomeNewsIntent {

    data class SelectCategory(val category: Constants.CATEGORY) : HomeNewsIntent()

}


