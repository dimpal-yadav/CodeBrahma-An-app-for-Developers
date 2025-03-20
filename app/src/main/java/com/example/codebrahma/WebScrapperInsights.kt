package com.example.codebrahma

import NewsArticle
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import retrofit2.HttpException
import java.io.IOException

@Composable
fun WebScrapperInsights() {
    var newsList by remember { mutableStateOf<List<NewsArticle>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            newsList = RetrofitInstance.api.getNews()
            isLoading = false
        } catch (e: IOException) {
            errorMessage = "Network error! Please check your connection."
            isLoading = false
        } catch (e: HttpException) {
            errorMessage = "Server error! Please try again later."
            isLoading = false
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor1 by infiniteTransition.animateColor(
        initialValue = Color(0xFF42A5F5),
        targetValue = Color(0xFFAB47BC),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedColor2 by infiniteTransition.animateColor(
        initialValue = Color(0xFF66BB6A),
        targetValue = Color(0xFFFF7043),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 102.dp, bottom = 80.dp)
            .background(Brush.linearGradient(listOf(animatedColor1, animatedColor2)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "📡 Web Scraped News", fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red, fontSize = 18.sp)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(newsList) { article ->
                        if (article.source == "GitHub Trending") {
                            GitHubNewsItem(article)
                        } else {
                            NewsItem(article)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: NewsArticle) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .clickable {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        ) {
            article.image?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 8.dp)
                )
            }
            Text(text = article.title, fontSize = 18.sp, color = Color.Black)
            Text(
                text = article.source,
                fontSize = 14.sp,
                color = Color.Gray,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun GitHubNewsItem(article: NewsArticle) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .clickable {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        ) {
            article.image?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "GitHub News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 8.dp)
                )
            }
            Text(text = "🔥 " + article.title, fontSize = 18.sp, color = Color.Yellow)
            Text(
                text = article.source,
                fontSize = 14.sp,
                color = Color.Cyan,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
