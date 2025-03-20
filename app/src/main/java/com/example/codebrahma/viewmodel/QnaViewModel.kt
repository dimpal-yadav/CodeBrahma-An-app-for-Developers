package com.example.codebrahma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codebrahma.api.QnaApiService
import com.example.codebrahma.api.QnaRetrofitInstance
import com.example.codebrahma.api.QnARequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QnaViewModel : ViewModel() {
    private val qnaApi: QnaApiService = QnaRetrofitInstance.api

    private val _qnaList = MutableStateFlow<List<QnARequest>>(emptyList())
    val qnaList: StateFlow<List<QnARequest>> = _qnaList

    private val _answer = MutableStateFlow<String?>(null)
    val answer: StateFlow<String?> = _answer

    fun addQnA(question: String, answer: String) {
        viewModelScope.launch {
            qnaApi.addQnA(QnARequest(question, answer))
        }
    }

    fun askQuestion(question: String) {
        viewModelScope.launch {
            val response = qnaApi.askQuestion(QnARequest(question))
            if (response.isSuccessful) {
                _answer.value = response.body()?.answer
            }
        }
    }

    fun listQnA() {
        viewModelScope.launch {
            val response = qnaApi.listQnA()
            if (response.isSuccessful) {
                _qnaList.value = response.body()?.qna_list ?: emptyList()
            }
        }
    }

    fun deleteQnA(question: String) {
        viewModelScope.launch {
            qnaApi.deleteQnA(QnARequest(question))
            listQnA()
        }
    }
}
