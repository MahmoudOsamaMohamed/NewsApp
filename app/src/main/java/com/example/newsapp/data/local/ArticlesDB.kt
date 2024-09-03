package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(SourceConverter::class, CategoryConverter::class)
abstract class ArticlesDB:RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}