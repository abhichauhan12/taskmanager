package com.example.taskmanager.utils

import android.app.PendingIntent
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun getFormattedTime(timeInMillis:Long) : String{
    val format = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
    val date = Date(timeInMillis)
    return format.format(date)
}

fun getFormattedDate(timeInMillis:Long) : String{
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = Date(timeInMillis)
    return format.format(date)
}

enum class Theme {
    SYSTEM,
    DARK,
    LIGHT
}


fun getTimeForAlarm(deadline: Long): Long {
    val currentTime = System.currentTimeMillis()
    val oneDay = TimeUnit.DAYS.toMillis(1)
    val oneHour = TimeUnit.HOURS.toMillis(1)

    return if (deadline - oneDay > currentTime) deadline - oneDay
    else deadline - oneHour
}

fun getPendingIntentFlag() : Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    else PendingIntent.FLAG_UPDATE_CURRENT
}
