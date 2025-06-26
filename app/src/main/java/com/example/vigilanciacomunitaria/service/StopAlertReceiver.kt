package com.example.vigilanciacomunitaria.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class StopAlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Parar o serviço
        context.stopService(Intent(context, PanicAlertService::class.java))

        // Cancelar a notificação
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(1)
    }
}