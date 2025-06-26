package com.example.vigilanciacomunitaria

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.example.vigilanciacomunitaria.util.NotificationUtil

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic("comunidade")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Inscrito no tópico 'comunidade'")
                } else {
                    Log.w(TAG, "Falha ao se inscrever no tópico", task.exception)
                }
            }

        // Obter token FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Falha ao obter token do FCM", task.exception)
                return@addOnCompleteListener
            }

            // Token recebido
            val token = task.result
            Log.d(TAG, "Token FCM: $token")
            // Aqui no futuro você pode enviar esse token para um backend
        }

        NotificationUtil.createNotificationChannel(this)

        val alertButton: Button = findViewById(R.id.alertButton)
        alertButton.setOnClickListener {
            NotificationUtil.sendPanicNotification(this)
        }
    }
}
