package com.example.taskmanager.utils

import android.graphics.Color
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.taskmanager.utils.TaskConstants.INVALID_COLOR
import java.lang.Exception
import java.util.regex.Pattern

object TaskConstants{
    const val  PRIORITY_DEFAULT = 3
    val  COLOR_DEFAULT = Color.parseColor("#00251a")
    val DEADLINE_DEFAULT = " "
    const val INVALID_COLOR = -1

}

object SettingsPrefsConstants{
    const val SETTING_PREFERENCE_NAME = "settings"
    const val KEY_SHOW_COMPLETED = "KEY_SHOW_COMPLETED"
    const val KEY_SORT_BY_PRIORITY = "KEY_SORT_BY_PRIORITY"
    const val KEY_SORT_BY_DEADLINE = "KEY_SORT_BY_DEADLINE"

    const val KEY_COLOR = "KEY_COLOR"

    val showCompletedKey = booleanPreferencesKey(KEY_SHOW_COMPLETED)
    val sortByPriorityKey = booleanPreferencesKey(KEY_SORT_BY_PRIORITY)
    val sortByDeadlineKey = booleanPreferencesKey(KEY_SORT_BY_DEADLINE)

    val colorKey = stringPreferencesKey(KEY_COLOR)

}
