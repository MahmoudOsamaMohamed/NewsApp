package com.example.newsapp.presentation.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.newsapp.common.Constants
import com.example.newsapp.data.model.Article
import com.example.newsapp.presentation.intent.HomeNewsIntent
import com.example.newsapp.presentation.viewmodel.HomeNewsViewModel



@Composable
fun HomeNewsScreen(

    viewModel: HomeNewsViewModel = hiltViewModel(),
    onClick: (Article) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column (
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(items = state.categories) { category ->
                Button(
                    onClick = { viewModel.handleIntent(HomeNewsIntent.SelectCategory(category
                    )) },
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.selectedCategory == category) Color.Blue else Color.Gray
                    )
                ) {
                    Text(text = category.value)
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }

        if (state.isLoading) {
            Log.d("HomeNewsScreen", state.toString())
            Spacer(modifier = Modifier.height(100.dp))
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),

                color = Color.Blue
            )
        } else {
            LazyColumn {
                items(state.articles) { article ->
                    ArticleItem(article,onClick)
                }
            }
        }
    }
}
    @Composable
    fun ArticleItem(article: Article, onClick: (Article) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(150.dp)
                .clickable(onClick = {
                    Log.d("ArticleItem", article.toString())
                    onClick(article)
                }),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
               modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = article.urlToImage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = article.source.name,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article.author?:"Anonymous",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                }
            }
        }

    }







