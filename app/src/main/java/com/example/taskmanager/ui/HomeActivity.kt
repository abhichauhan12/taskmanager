package com.example.taskmanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R

class HomeActivity : AppCompatActivity() {

    private lateinit var utilsViewModel :UtilsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        utilsViewModel=ViewModelProvider(this,UtilsViewModel.Factory())[UtilsViewModel::class.java]



    }
}