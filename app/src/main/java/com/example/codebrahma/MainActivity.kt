package com.example.codebrahma

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.codebrahma.auth.FirebaseAuthManager
import com.example.codebrahma.ui.HomeScreen
import com.example.codebrahma.ui.LoginScreen
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionsIfNeeded()
        val user = FirebaseAuthManager.getCurrentUser()
        val firestore = FirebaseFirestore.getInstance()
        if (user != null) {
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    val isProfileComplete = document.getString("name")?.isNotEmpty() == true &&
                            document.getString("organization")?.isNotEmpty() == true &&
                            document.getString("focused_area")?.isNotEmpty() == true

                    if (isProfileComplete) {
                        startActivity(Intent(this, FirstActivity::class.java))
                        finish()
                    } else {
                        setContent {
                            HomeScreen {
                                startActivity(Intent(this@MainActivity, FirstActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
        } else {
            setContent {
                LoginScreen(this) {
                    recreate()
                }
            }
        }
    }
    private fun requestPermissionsIfNeeded() {
        val permissionsToRequest = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_CALENDAR)
            permissionsToRequest.add(Manifest.permission.WRITE_CALENDAR)
        }
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 1001)
        }
    }
}
