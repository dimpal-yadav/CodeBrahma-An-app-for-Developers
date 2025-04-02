package com.example.codebrahma

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codebrahma.viewmodel.QnaViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SnippetScreen(viewModel: QnaViewModel = viewModel()) {
    val context = LocalContext.current
    val snippets by viewModel.snippets.collectAsState()
    var description by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var showCodeInput by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("Loading...") }

    val firestore = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    var likeCount by remember { mutableStateOf(0) }
    var shareCount by remember { mutableStateOf(0) }
    var isLiked by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    userName = document.getString("name") ?: "N/A"
                }
                .addOnFailureListener {
                    userName = "Failed to load"
                }
        }
    }

    LaunchedEffect(Unit) { viewModel.fetchSnippets() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.snippetbg),
            contentDescription = "Marvel Themed Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "$userName's Code Snippets",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = {},
                label = { Text("Title", color = Color.White) },
                modifier = Modifier.fillMaxWidth(0.8f),
                readOnly = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = Color.Black) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { showCodeInput = !showCodeInput },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(
                    if (showCodeInput) "Hide Code Input" else "Add Code Snippet",
                    color = Color.White
                )
            }

            if (showCodeInput) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Enter Python Code", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Button(
                onClick = {
                    if (userName.isNotEmpty() && description.isNotEmpty() && code.isNotEmpty()) {
                        viewModel.addSnippet(userName, description, code)
                        Toast.makeText(context, "Snippet Added", Toast.LENGTH_SHORT).show()
                        description = ""
                        code = ""
                        showCodeInput = false
                    } else {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                Text("Save Snippet", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Saved Snippets",
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(snippets) { index, snippet ->
                    val animatedBrushColor by animateColorAsState(
                        targetValue = Color(0xFF6200EA),
                        label = "Animated Brush Color"
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                            .background(animatedBrushColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "#${index + 1} ${snippet.title}",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = snippet.description,
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = snippet.code_snippet,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(onClick = {
                                        if (!isLiked) {
                                            isLiked = true
                                            likeCount++
                                        } else {
                                            likeCount++
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Like",
                                            tint = if (isLiked) Color.Red else Color.Gray
                                        )
                                    }
                                    Text("$likeCount", color = Color.Black, fontSize = 14.sp)

                                    IconButton(onClick = {
                                        shareCount++
                                    }) {
                                        Icon(
                                            Icons.Default.Share,
                                            contentDescription = "Share",
                                            tint = Color.Black
                                        )
                                    }
                                    Text("$shareCount", color = Color.Black, fontSize = 14.sp)

                                    IconButton(onClick = { /* Follow action */ }) {
                                        Icon(
                                            Icons.Default.AccountCircle,
                                            contentDescription = "Follow",
                                            tint = Color.Green
                                        )
                                    }
                                }

                                Button(
                                    onClick = { snippet.id?.let { viewModel.deleteSnippet(it) } },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) {
                                    Text("Delete", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSnippetScreen() {
    SnippetScreen()
}
