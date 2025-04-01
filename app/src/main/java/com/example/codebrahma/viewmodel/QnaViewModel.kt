package com.example.codebrahma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codebrahma.network.ApiService
import com.example.codebrahma.network.RetrofitClient
import com.example.codebrahma.network.Snippet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QnaViewModel : ViewModel() {
    private val apiService: ApiService = RetrofitClient.instance

    private val _snippets = MutableStateFlow<List<Snippet>>(emptyList())
    val snippets = _snippets.asStateFlow()

    fun fetchSnippets() {
        viewModelScope.launch {
            try {
                val response = apiService.listSnippets()
                _snippets.value = response.snippets ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addSnippet(title: String, description: String, code: String) {
        viewModelScope.launch {
            try {
                val response = apiService.addSnippet(Snippet(title = title, description = description, code_snippet = code))
                val newSnippet = Snippet(id = (_snippets.value.size + 1), title = title, description = description, code_snippet = code)

                _snippets.value = _snippets.value + newSnippet

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateSnippet(id: Int, title: String, description: String, code: String) {
        viewModelScope.launch {
            try {
                apiService.updateSnippet(Snippet(id = id, title = title, description = description, code_snippet = code))
                fetchSnippets()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun deleteSnippet(id: Int) {
        viewModelScope.launch {
            try {
                apiService.deleteSnippet(id)
                fetchSnippets()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
