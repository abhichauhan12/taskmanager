package com.example.taskmanager.utils

import android.graphics.Color
import android.util.Log
import com.example.taskmanager.utils.TaskConstants.INVALID_COLOR
import java.lang.Exception
import java.util.regex.Pattern

object TaskConstants{
    const val  PRIORITY_DEFAULT = 3
    val  COLOR_DEFAULT = Color.parseColor("#00251a")
    val DEADLINE_DEFAULT = " "
    const val INVALID_COLOR = -1

}

fun getParsedColor(color : String) : Int {
    val regex = Pattern.compile("[$&+,:;=\\\\?@|/'<>.^*()%!-]");
    if (color.isBlank() || color.first() != '#' || color.length != 7 || regex.matcher(color).find()) return INVALID_COLOR
    return try {
        Color.parseColor(color)
    }catch (e:Exception){
        INVALID_COLOR
    }

}




