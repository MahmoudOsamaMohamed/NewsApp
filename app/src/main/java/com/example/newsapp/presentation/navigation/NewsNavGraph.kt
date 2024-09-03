package com.example.newsapp.presentation.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.common.Constants.ARTICLE_SCREEN
import com.example.newsapp.common.Constants.HOME_SCREEN
import com.example.newsapp.data.model.Article
import com.example.newsapp.presentation.screen.ArticleDetailsScreen
import com.example.newsapp.presentation.screen.HomeNewsScreen
import com.google.gson.Gson

@Composable
fun NewsNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_SCREEN) {
        composable(HOME_SCREEN) {
            HomeNewsScreen { article ->
                navController.navigate("${ARTICLE_SCREEN}/${Uri.encode(Gson().toJson(article))}")
            }
        }
        composable(
            route = "${ARTICLE_SCREEN}/{article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")
            val article = Gson().fromJson(articleJson, Article::class.java)
            ArticleDetailsScreen(article = article)
        }

    }
}
