package com.example.taskmanager.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.taskmanager.domain.model.AuthStatus
import com.example.taskmanager.domain.model.User
import com.example.taskmanager.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // first : status, second : error string
    private val _authStatus = MutableStateFlow<Pair<AuthStatus?, String>>(null to "")
    val authStatus = _authStatus.asStateFlow()

    fun isUserLoggedIn() = authRepository.getCurrentLoggedInUser() != null

    fun signUp(user : User, onSuccess : () -> Unit, onFailure : (String) -> Unit) {
        updateAuthStatus(AuthStatus.LOADING, "")
        authRepository.signUp(user, onSuccess, onFailure)
    }

    fun updateAuthStatus(status: AuthStatus, errorString: String) {
        _authStatus.value = status to errorString
    }

}