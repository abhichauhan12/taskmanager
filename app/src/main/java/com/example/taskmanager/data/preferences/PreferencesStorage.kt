package com.example.taskmanager.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanager.utils.SettingsPrefsConstants


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingsPrefsConstants.SETTING_PREFERENCE_NAME)
