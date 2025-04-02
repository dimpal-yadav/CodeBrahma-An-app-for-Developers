package com.example.codebrahma

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch

@Composable
fun Home() {
    val items = listOf("Python", "Kotlin", "Java", "C++", "JavaScript")
    val coroutineScope = rememberCoroutineScope()
    var expandedCard by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Welcome to Code Brahma",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow {
            items(items) { language ->
                LanguageCard(language)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.indices.toList()) { index ->
                ExpandableCard(
                    title = "${items[index]} Resources",
                    isExpanded = expandedCard == index,
                    onExpandChange = {
                        coroutineScope.launch {
                            if (expandedCard == index) {
                                expandedCard = -1
                            } else {
                                expandedCard = index
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LanguageCard(language: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 150.dp, height = 80.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = language, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ExpandableCard(title: String, isExpanded: Boolean, onExpandChange: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242))
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(16.dp)
        ) {
            Text(text = title, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onExpandChange, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))) {
                Text(text = if (isExpanded) "Collapse" else "Expand", color = Color.White)
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Here are your resources for $title", color = Color.LightGray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    Home()
}
