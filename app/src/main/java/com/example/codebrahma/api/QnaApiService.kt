package com.example.codebrahma.api

import retrofit2.http.*

data class QnARequest(val question: String, val answer: String? = null)
data class QnAResponse(val answer: String)
data class QnAListResponse(val qna_list: List<QnARequest>)

interface QnaApiService {
    @POST("/add_qna")
    suspend fun addQnA(@Body request: QnARequest): retrofit2.Response<Unit>

    @POST("/ask")
    suspend fun askQuestion(@Body request: QnARequest): retrofit2.Response<QnAResponse>

    @GET("/list_qna")
    suspend fun listQnA(): retrofit2.Response<QnAListResponse>

    @DELETE("/delete_qna")
    suspend fun deleteQnA(@Body request: QnARequest): retrofit2.Response<Unit>
}
