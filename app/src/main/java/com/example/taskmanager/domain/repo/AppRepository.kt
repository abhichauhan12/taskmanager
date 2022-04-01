package com.example.taskmanager.domain.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.taskmanager.utils.SettingsPrefsConstants
import com.example.taskmanager.utils.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AppRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

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