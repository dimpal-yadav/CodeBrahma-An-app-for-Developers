package com.example.codebrahma.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.codebrahma.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

class GitHubSignInActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val data: Uri? = intent?.data
        if (data != null && data.toString().startsWith("codebrahma://callback")) {
            handleGitHubRedirect(data)
        } else {
            initiateGitHubLogin()
        }
    }

    private fun initiateGitHubLogin() {
        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("allow_signup", "false")
        provider.scopes = listOf("read:user", "user:email")

        auth.startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener { authResult ->
                Log.d("GitHubSignIn", "Login successful: ${authResult.user?.displayName}")
                navigateToMainActivity()
            }
            .addOnFailureListener { e ->
                Log.e("GitHubSignIn", "Login failed: ${e.message}")
                finish()
            }
    }

    private fun handleGitHubRedirect(data: Uri) {
        val code = data.getQueryParameter("code")
        if (code != null) {
            Log.d("GitHubSignIn", "Received GitHub Auth Code: $code")
            exchangeCodeForToken(code)
        } else {
            Log.e("GitHubSignIn", "No auth code received from GitHub")
            finish()
        }
    }

    private fun exchangeCodeForToken(code: String) {
        val provider = OAuthProvider.newBuilder("github.com")

        auth.pendingAuthResult?.addOnSuccessListener { authResult ->
            Log.d("GitHubSignIn", "Firebase Authentication successful")
            navigateToMainActivity()
        }?.addOnFailureListener { e ->
            Log.e("GitHubSignIn", "Firebase Authentication failed: ${e.message}")
            finish()
        } ?: initiateGitHubLogin()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
