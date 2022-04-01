package com.example.taskmanager.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.taskmanager.data.database.TaskDatabase
import com.example.taskmanager.utils.SettingsPrefsConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingsPrefsConstants.SETTING_PREFERENCE_NAME)

    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context) : TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "task_db").build()
    }

    @Provides
    fun provideDatastore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return context.dataStore
    }
}