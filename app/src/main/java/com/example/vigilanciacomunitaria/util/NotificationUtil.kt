package com.example.vigilanciacomunitaria.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.vigilanciacomunitaria.ui.MainActivity
import com.example.vigilanciacomunitaria.R
import com.example.vigilanciacomunitaria.service.PanicAlertService

// ✅ Canal de notificação para alertas
const val CHANNEL_ID = "panic_alert_channel"

object NotificationUtil {

    // Cria o canal de notificação (Android O+)
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alerta de Pânico",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Receber alertas urgentes"
                enableLights(true)
                enableVibration(true)
                setSound(
                    Uri.parse("android.resource://${context.packageName}/${R.raw.siren}"),
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                )
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Envia uma notificação de pânico
    fun sendPanicNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alert)
            .setContentTitle("🚨 ALERTA DE PÂNICO!")
            .setContentText("Um vizinho ativou o modo pânico.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(context).notify(1, notification.build())

        // Iniciar o som da sirene
        context.startService(Intent(context, PanicAlertService::class.java))
    }
}