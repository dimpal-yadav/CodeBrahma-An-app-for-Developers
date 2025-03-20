package com.example.codebrahma

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codebrahma.auth.FirebaseAuthManager
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Profile() {
    val firestore = FirebaseFirestore.getInstance()
    val user = FirebaseAuthManager.getCurrentUser()

    var userName by remember { mutableStateOf("Loading...") }
    var userOrg by remember { mutableStateOf("Loading...") }
    var userEmail by remember { mutableStateOf("Loading...") }
    var userFocusedArea by remember { mutableStateOf("Loading...") }

    LaunchedEffect(user) {
        user?.let {
            firestore.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    userName = document.getString("name") ?: "N/A"
                    userOrg = document.getString("organization") ?: "N/A"
                    userEmail = document.getString("email") ?: "N/A"
                    userFocusedArea = document.getString("focused_area") ?: "N/A"
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)), // Light gray background for contrast
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Details",
                fontSize = 28.sp,
                color = Color(0xFF1565C0),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            ProfileCard(label = "Name", value = userName)
            ProfileCard(label = "Organization", value = userOrg)
            ProfileCard(label = "Email", value = userEmail)
            ProfileCard(label = "Focused Area", value = userFocusedArea)
        }
    }
}

@Composable
fun ProfileCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp)), // Adds shadow & rounded corners
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // Adds elevation for depth
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}
