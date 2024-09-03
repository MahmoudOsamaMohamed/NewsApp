package com.example.newsapp.common

object Constants {

    const val COUNTRY_CODE: String = "us"
    const val HOME_SCREEN: String = "Home"
    const val ARTICLE_SCREEN: String = "Article"
    const val BASE_URL = "https://saurav.tech/NewsAPI/top-headlines/"
    enum class CATEGORY(val value: String) {
        BUSINESS("business"),
        ENTERTAINMENT("entertainment"),
        GENERAL("general"),
        HEALTH("health"),
        SCIENCE("science"),
        SPORTS("sports"),
        TECHNOLOGY("technology"),
    }


}
fun editCategory(category: String): Constants.CATEGORY {
    Constants.CATEGORY.entries.forEach {
        if (it.value == category) return it
    }
    return Constants.CATEGORY.GENERAL
}
/*
    business
entertainment
general
health
science
sports
technology

 */