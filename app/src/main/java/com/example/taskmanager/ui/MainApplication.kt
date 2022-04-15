@file:Suppress("NestedLambdaShadowedImplicitParameter", "unused")

package com.example.taskmanager.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.example.taskmanager.domain.repo.AppRepository
import com.example.taskmanager.utils.Theme
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject lateinit var appRepository: AppRepository
    private val scope : CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        initializeAmplify()
        observeTheme()
    }

    private fun initializeAmplify() {
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(this)
    }

    private fun observeTheme() {
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