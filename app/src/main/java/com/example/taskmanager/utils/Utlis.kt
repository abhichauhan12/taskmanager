package com.example.taskmanager.utils

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedTime(timeInMillis:Long) : String{
    val format = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
    val date = Date(timeInMillis)
    return format.format(date)
}