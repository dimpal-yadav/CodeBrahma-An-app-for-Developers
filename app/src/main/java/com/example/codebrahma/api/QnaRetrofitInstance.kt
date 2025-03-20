package com.example.codebrahma.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QnaRetrofitInstance {
    private const val BASE_URL = "https://your-huggingface-space-url"    //

    val api: QnaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QnaApiService::class.java)
    }
}
