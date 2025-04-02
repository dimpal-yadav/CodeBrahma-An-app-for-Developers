package com.example.codebrahma.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance2 {
    private const val BASE_URL = "https://apis-qgxq.onrender.com"

    val api: PythonWebCompilerApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PythonWebCompilerApi::class.java)
    }
}

