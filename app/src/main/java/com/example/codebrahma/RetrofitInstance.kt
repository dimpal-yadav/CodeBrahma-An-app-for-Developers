package com.example.codebrahma

import NewsArticle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsApi {
    @GET("news")
    suspend fun getNews(): List<NewsArticle>
}

object RetrofitInstance {
    val api: NewsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://starkbyte45896-coding-news-scrapping.hf.space/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}
