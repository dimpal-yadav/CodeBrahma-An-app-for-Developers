package com.example.codebrahma

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    private val tag = "FCMService"

    override fun onNewToken(token: String) {
        Log.d(tag, "New FCM Token: $token")

        // Save token locally
        saveTokenLocally(token)

        // Send token to Firestore (User-based storage)
        sendTokenToFirestore(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(tag, "FCM Message From: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d(tag, "Message Notification Body: ${it.body}")
            val notification = MyNotification(applicationContext, it.title.toString(), it.body.toString())
            notification.FirNotification()
        }
    }

    private fun saveTokenLocally(token: String) {
        val sharedPref = getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("fcm_token", token)
            apply()
        }
    }

    private fun sendTokenToFirestore(token: String) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(user.uid)

            userRef.update("fcm_token", token)
                .addOnSuccessListener { Log.d(tag, "FCM Token updated in Firestore") }
                .addOnFailureListener { e -> Log.e(tag, "Error updating token", e) }
        } else {
            Log.e(tag, "User not authenticated. Token not sent to Firestore.")
        }
    }
}
