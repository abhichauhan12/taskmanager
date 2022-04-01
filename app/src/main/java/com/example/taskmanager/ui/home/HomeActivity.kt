package com.example.taskmanager.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}