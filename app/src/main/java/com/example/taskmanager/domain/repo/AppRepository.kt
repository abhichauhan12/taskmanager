package com.example.taskmanager.domain.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.taskmanager.data.preferences.dataStore
import com.example.taskmanager.utils.SettingsPrefsConstants
import com.example.taskmanager.utils.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class AppRepository(private val dataStore: DataStore<Preferences>) {
    companion object {
        private var instance: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            return instance ?: synchronized(this) {
                instance = AppRepository(dataStore = context.dataStore)
                instance!!
            }
        }
    }

    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    val appTheme = dataStore.data.map {
        it[SettingsPrefsConstants.appThemeKey] ?: Theme.SYSTEM.name
    }.stateIn(scope, SharingStarted.Eagerly, null)

    suspend fun updateAppTheme(theme : Theme){
        dataStore.edit {
            it[SettingsPrefsConstants.appThemeKey] = theme.name
        }
    }
}