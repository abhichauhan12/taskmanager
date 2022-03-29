package com.example.taskmanager.domain.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.taskmanager.R
import com.example.taskmanager.utils.BundleConstants.TASK
import kotlin.random.Random

class DeadlineReceiver : BroadcastReceiver() {
    private lateinit var notificationManager : NotificationManager

    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra(TASK) ?: return
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        showNotification(context, title)
    }

    private fun showNotification(context: Context, title: String) {
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

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_back)
            .setContentTitle("Deadline")
            .setContentText("Your $title deadline is near")
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}