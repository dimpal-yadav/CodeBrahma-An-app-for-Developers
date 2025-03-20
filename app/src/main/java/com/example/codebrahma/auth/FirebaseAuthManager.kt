package com.example.codebrahma.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseAuthManager {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun signInWithGitHub(activity: Activity, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val provider = OAuthProvider.newBuilder("github.com")
        val pendingResultTask = auth.pendingAuthResult

        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    Log.d("GitHubAuth", "Successfully logged in: ${it.user?.email}")
                    saveUserToFirestore(it.user?.uid ?: "", it.user?.email ?: "Unknown")
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.e("GitHubAuth", "Login Failed: ${e.message}")
                    onFailure(e.message ?: "Unknown error")
                }
        } else {
            auth.startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener {
                    Log.d("GitHubAuth", "Successfully logged in: ${it.user?.email}")
                    saveUserToFirestore(it.user?.uid ?: "", it.user?.email ?: "Unknown")
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.e("GitHubAuth", "Login Failed: ${e.message}")
                    onFailure(e.message ?: "Unknown error")
                }
        }
    }

    private fun saveUserToFirestore(uid: String, email: String) {
        val userMap = hashMapOf(
            "uid" to uid,
            "email" to email
        )

        firestore.collection("users").document(uid).set(userMap)
            .addOnSuccessListener { Log.d("Firestore", "User data saved") }
            .addOnFailureListener { e -> Log.e("Firestore", "Error saving user: ${e.message}") }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser
}
