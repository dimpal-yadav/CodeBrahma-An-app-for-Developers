package com.example.codebrahma.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.codebrahma.FirstActivity
import com.example.codebrahma.R
import com.example.codebrahma.auth.FirebaseAuthManager
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val user = FirebaseAuthManager.getCurrentUser()

    var userName by remember { mutableStateOf("") }
    var userOrg by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userFocusedArea by remember { mutableStateOf("") }
    var isProfileComplete by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val focusedAreas = listOf(
        "Python", "Java", "Jetpack Compose", "HTML", "CSS", "JavaScript", "Web Dev", "Machine Learning", "Blockchain"
    )

    LaunchedEffect(user) {
        user?.let {
            firestore.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    userName = document.getString("name") ?: ""
                    userOrg = document.getString("organization") ?: ""
                    userEmail = document.getString("email") ?: ""
                    userFocusedArea = document.getString("focused_area") ?: ""
                    isProfileComplete = userName.isNotEmpty() && userOrg.isNotEmpty() && userFocusedArea.isNotEmpty()
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(R.drawable.codebrahmaseconloginwall), contentScale = ContentScale.Crop)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Complete Your Profile",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            UserOutlinedTextField("Enter your Name", userName) { userName = it }
            UserOutlinedTextField("Organization/Company", userOrg) { userOrg = it }
            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth().padding(start = 34.dp)
                ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xAA0000000))
                ) {
                    Text(text = userFocusedArea.ifEmpty { "Select Your Focused Area" }, color = Color.White)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    focusedAreas.forEach { area ->
                        DropdownMenuItem(text = { Text(text = area) }, onClick = {
                            userFocusedArea = area
                            expanded = false
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    user?.let {
                        val userData = mapOf(
                            "name" to userName,
                            "organization" to userOrg,
                            "focused_area" to userFocusedArea,
                            "email" to userEmail
                        )
                        firestore.collection("users").document(it.uid).set(userData)
                            .addOnSuccessListener { isProfileComplete = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(18.dp),
                enabled = userName.isNotEmpty() && userOrg.isNotEmpty() && userFocusedArea.isNotEmpty() && userEmail.isNotEmpty()
            ) { Text(text = "Save Profile", color = Color.White) }

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    val intent = Intent(context, FirstActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xAA000000)),
                enabled = isProfileComplete
            ) {
                Text(text = "Start the Experience", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserOutlinedTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xAA000000),
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Yellow,
            focusedTextColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions.Default,
        singleLine = true
    )
}