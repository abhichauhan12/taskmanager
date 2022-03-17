package com.example.taskmanager.utils

import android.graphics.Color
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

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

fun getParsedColor(color : String) : Int {
    val regex = Pattern.compile("[$&+,:;=\\\\?@|/'<>.^*()%!-]");
    if (color.isBlank() || color.first() != '#' || color.length != 7 || regex.matcher(color).find()) return TaskConstants.INVALID_COLOR
    return try {
        Color.parseColor(color)
    }catch (e: Exception){
        TaskConstants.INVALID_COLOR
    }
}
