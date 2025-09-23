package com.example.codebrahma_anappfordevs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codebrahma_anappfordevs.ui.theme.CodeBrahmaAnAppForDevsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeBrahmaAnAppForDevsTheme {
                Scaffold(modifier = Modifier.fillMaxSize().fillMaxHeight()) { innerPadding ->
                    Greeting(
                        name = "Code Brahma",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.fillMaxHeight().padding(top = 48.dp
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeBrahmaAnAppForDevsTheme {
        Greeting("CodeBrahma")
    }
}