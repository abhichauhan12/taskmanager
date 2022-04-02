@file:Suppress("unused")

package com.example.taskmanager.utils

import android.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object BundleConstants {
    const val TASK: String = "task"
}

object TaskConstants {
    const val PRIORITY_DEFAULT = 3
    val COLOR_DEFAULT = Color.parseColor("#00251a")
    const val DEADLINE_DEFAULT = " "
    const val INVALID_COLOR = -1
    const val DEFAULT_DEADLINE = 0L
}

object SettingsPrefsConstants {
    const val SETTING_PREFERENCE_NAME = "settings"

    private const val KEY_SHOW_COMPLETED = "KEY_SHOW_COMPLETED"
    private const val KEY_SORT_BY_PRIORITY = "KEY_SORT_BY_PRIORITY"
    private const val KEY_SORT_BY_DEADLINE = "KEY_SORT_BY_DEADLINE"
    private const val KEY_APP_THEME = "KEY_APP_THEME"

    val showCompletedKey = booleanPreferencesKey(KEY_SHOW_COMPLETED)
    val sortByPriorityKey = booleanPreferencesKey(KEY_SORT_BY_PRIORITY)
    val sortByDeadlineKey = booleanPreferencesKey(KEY_SORT_BY_DEADLINE)
    val appThemeKey = stringPreferencesKey(KEY_APP_THEME)
}
