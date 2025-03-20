package com.example.codebrahma.auth

sealed class AuthState {
    object Loading : AuthState()
    object SignedOut : AuthState()
    data class SignedIn(val email: String?) : AuthState()
}
