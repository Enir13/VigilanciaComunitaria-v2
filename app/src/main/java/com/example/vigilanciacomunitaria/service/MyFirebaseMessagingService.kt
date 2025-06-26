package com.example.vigilanciacomunitaria.service

import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.vigilanciacomunitaria.util.NotificationUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Verifica se a mensagem contém dados personalizados
        if (remoteMessage.data.isNotEmpty()) {
            val alertType = remoteMessage.data["type"]
            if (alertType == "panic") {
                NotificationUtil.sendPanicNotification(this)
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Novo token: $token")
        // Salve o token em algum lugar, como SharedPreferences ou Firebase Database
    }
}