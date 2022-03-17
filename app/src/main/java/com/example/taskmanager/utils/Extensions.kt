package com.example.taskmanager.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanager.utils.SettingsPrefsConstants.SETTING_PREFERENCE_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTING_PREFERENCE_NAME)
