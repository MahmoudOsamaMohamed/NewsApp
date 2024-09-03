package com.example.newsapp.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.newsapp.common.Constants.BASE_URL
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.ArticlesDB
import com.example.newsapp.data.remote.ArticlesApi
import com.example.newsapp.data.repository.ArticlesRepositoryImpl
import com.example.newsapp.domain.repository.ArticlesRepository
import com.example.newsapp.domain.usecase.FetchArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideArticlesApi() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ArticlesApi::class.java)

    @Provides
    @Singleton
    fun provideArticlesRepository(
        api: ArticlesApi,
        dao: ArticleDao
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(api, dao)
    }

    @Provides
    fun provideFetchArticlesUseCase(repository: ArticlesRepository,connectivityManager: ConnectivityManager): FetchArticlesUseCase {
        return FetchArticlesUseCase(repository,connectivityManager)
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ArticlesDB {
        return Room.databaseBuilder(
            appContext,
            ArticlesDB::class.java,
            "articles_db"
        ).build()
    }

    @Provides
    fun provideArticleDao(db: ArticlesDB): ArticleDao {
        return db.getArticleDao()
    }

}