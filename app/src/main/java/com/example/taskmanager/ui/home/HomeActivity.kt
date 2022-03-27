package com.example.taskmanager.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.taskmanager.R
import com.example.taskmanager.domain.receivers.TestBR
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var utilsViewModel: UtilsViewModel
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        utilsViewModel = UtilsViewModel.get(this)

        showTestNotification()
    }

    private fun showTestNotification() {
        val channelId = "channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Test channel"
            val channelDesc = "Test channel desc"

            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply { description = channelDesc }
            )
        }

        val ankitIntent = Intent(this, TestBR::class.java).apply {
            action = "Show Ankit"
        }
        val ankitPendingIntent = PendingIntent.getBroadcast(this, 0, ankitIntent, getPendingIntentFlag())

        val abhishekIntent = Intent(this, TestBR::class.java).apply {
            action = "Show Abhishek"
        }
        val abhishekPendingIntent = PendingIntent.getBroadcast(this, 1, abhishekIntent, getPendingIntentFlag())

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_back)
            .setContentTitle("Title")
            .setContentText("Description")
            .addAction(R.drawable.ic_back, "Show Ankit", ankitPendingIntent)
            .addAction(R.drawable.ic_back, "Show Abhishek", abhishekPendingIntent)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .build()

        notificationManager.notify(100, notification)
    }

    private fun getPendingIntentFlag() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
               PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        else PendingIntent.FLAG_UPDATE_CURRENT
    }
}