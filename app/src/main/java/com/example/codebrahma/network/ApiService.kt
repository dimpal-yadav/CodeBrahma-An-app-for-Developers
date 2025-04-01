//this is API Service from Flask Backend for SnippetScreen.kt

package com.example.codebrahma.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class Snippet(
    val id: Int? = null,
    val title: String,
    val description: String,
    val code_snippet: String
)
data class ApiResponse(val message: String, val snippets: List<Snippet>?)

interface ApiService {
    @POST("/add_snippet")
    suspend fun addSnippet(@Body snippet: Snippet): ApiResponse
    @GET("/list_snippets")
    suspend fun listSnippets(): ApiResponse
    @PUT("/update_snippet")
    suspend fun updateSnippet(@Body snippet: Snippet): ApiResponse
    @DELETE("/delete_snippet")
    suspend fun deleteSnippet(@Query("id") id: Int): ApiResponse
}
object RetrofitClient {
    private const val BASE_URL = "https://raviai.onrender.com"
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
