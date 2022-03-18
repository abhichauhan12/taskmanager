package com.example.taskmanager.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.R
import com.example.taskmanager.ui.home.viewmodels.UtilsViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var utilsViewModel: UtilsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        utilsViewModel = UtilsViewModel.get(this)
    }
}