package com.example.taskmanager.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.taskmanager.domain.repo.AppRepository
import com.example.taskmanager.utils.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainApplication : Application() {

    private val appRepository by lazy { AppRepository.getInstance(this) }

    private val scope : CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        scope.launch {
            appRepository.appTheme.collect {
                val theme = it?.let { Theme.valueOf(it) }
                theme?.let {
                    when(it){
                        Theme.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        Theme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }

}