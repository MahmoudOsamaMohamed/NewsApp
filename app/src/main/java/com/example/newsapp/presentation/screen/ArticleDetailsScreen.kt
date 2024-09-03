package com.example.newsapp.presentation.screen

import androidx.compose.foundation.Image
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.data.model.Article

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp


@Composable
fun ArticleDetailsScreen(
    context: Context = LocalContext.current,
    article: Article?) {

    Log.d("ArticleDetailsScreen", article.toString())
    article?.let {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)

        ) {

            Image(
                painter = rememberAsyncImagePainter(model = it.urlToImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.source.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.author ?: "",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.content ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Published at: " + article.publishedAt.replace("T", " ").replace("Z", ""),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Box(modifier = Modifier.fillMaxSize().
        padding(bottom = 16.dp)) {
            Button(
                modifier = Modifier.align(Alignment.BottomEnd),
                colors = ButtonDefaults.buttonColors(
                    containerColor =  Color.Blue
                ),
                onClick = { shareArticleLink(context,article.url) }) {
                Icon(
                    imageVector = Icons.Default.Share, // Use a predefined share icon
                    contentDescription = "Share",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Adds space between icon and text
                Text("Share")
            }
            Button(
                modifier = Modifier.align(Alignment.BottomStart),

                colors = ButtonDefaults.buttonColors(
                    containerColor =  Color.Blue
                ),
                onClick = { openInBrowser(context,article.url) }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow, // Use a predefined share icon
                    contentDescription = "Open in browser",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Adds space between icon and text
                Text("Open in browser")
            }
        }
    }
}

fun openInBrowser(context: Context,url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

fun shareArticleLink(context: Context, url: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val chooserIntent = Intent.createChooser(shareIntent, "Share Article Link via")
    context.startActivity(chooserIntent)
}

