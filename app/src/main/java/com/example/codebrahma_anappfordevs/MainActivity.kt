package com.example.codebrahma_anappfordevs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.codebrahma_anappfordevs.ui.login.LoginScreen
import com.example.codebrahma_anappfordevs.ui.theme.CodeBrahmaAnAppForDevsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables drawing edge-to-edge (status bar, nav bar)
        setContent {
            CodeBrahmaAnAppForDevsTheme {
                // 👇 Loads the Login UI
                LoginScreen()
            }
        }
    }
}
