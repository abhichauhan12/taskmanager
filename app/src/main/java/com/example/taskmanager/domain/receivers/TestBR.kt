package com.example.taskmanager.domain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class TestBR : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> Toast.makeText(context, "Phone rebooted", Toast.LENGTH_SHORT).show()
            "Show Ankit" -> Toast.makeText(context, "Ankit yaayy", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(context, "Abhishek yaayy", Toast.LENGTH_LONG).show()
        }
    }
}