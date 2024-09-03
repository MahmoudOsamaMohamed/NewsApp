package com.example.newsapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.common.Constants
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "articles")
@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String?,
    var category: Constants.CATEGORY  = Constants.CATEGORY.GENERAL
):Parcelable
