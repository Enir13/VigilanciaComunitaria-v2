package com.example.vigilanciacomunitaria.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.vigilanciacomunitaria.R
import com.example.vigilanciacomunitaria.util.CHANNEL_ID

class PanicAlertService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        playSiren()
        return START_STICKY
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🚨 Vigilância Ativa")
            .setContentText("O alarme de pânico está ativo.")
            .setSmallIcon(R.drawable.ic_alert)
            .build()

        startForeground(1, notification)
    }

    private fun playSiren() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.siren).apply {
                isLooping = true
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        mediaPlayer?.let {
            it.stop()
            it.release()
        }
        mediaPlayer = null
        super.onDestroy()
    }
}