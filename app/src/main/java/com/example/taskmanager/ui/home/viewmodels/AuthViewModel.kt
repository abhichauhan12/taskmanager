package com.example.taskmanager.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskmanager.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun isUserLoggedIn() = authRepository.getCurrentLoggedInUser() != null
}