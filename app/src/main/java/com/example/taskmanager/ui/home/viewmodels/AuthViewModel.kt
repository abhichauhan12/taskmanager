@file:Suppress("unused")

package com.example.taskmanager.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskmanager.domain.model.*
import com.example.taskmanager.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpStatus = MutableStateFlow<SignUpStatus?>(null)
    val signUpStatus = _signUpStatus.asStateFlow()

    private val _signInStatus = MutableStateFlow<SignInStatus?>(null)
    val signInStatus = _signInStatus.asStateFlow()

    private val _signOutStatus = MutableStateFlow<SignOutStatus?>(null)
    val signOutStatus = _signOutStatus.asStateFlow()

    private val _authStatus = MutableStateFlow<AuthStatus?>(null)
    val authStatus = _authStatus.asStateFlow()

    fun isUserLoggedIn() = authRepository.getCurrentLoggedInUser() != null

    fun isUserSignedIn() {
        _authStatus.value = AuthStatus.CHECKING_SESSION
        authRepository.getCurrentAuthSession { signedIn ->
            _authStatus.value = if (signedIn) AuthStatus.USER_SIGNED_IN else AuthStatus.USER_NOT_SIGNED_IN
        }
    }

    fun signUp(user : User) {
        updateSignUpStatus(SignUpStatus.SIGN_UP_STARTED)
        authRepository.signUp(user, onSuccess = ::updateSignUpStatus, onFailure = ::updateSignUpStatus)
    }

    fun login(user : User) {
        updateSignInStatus(SignInStatus.SIGN_IN_STARTED)
        authRepository.login(user, onSuccess = ::updateSignInStatus, onFailure = ::updateSignInStatus)
    }

    fun verifyOtp(user: User, code : String) {
        updateSignUpStatus(SignUpStatus.VERIFY_STARTED)
        authRepository.verifyOtp(user, code, onSuccess = ::updateSignUpStatus, onFailure = ::updateSignUpStatus)
    }

    fun signOut() {
        updateSignOutStatus(SignOutStatus.SIGNING_OUT)
        authRepository.signOut(onSuccess = ::updateSignOutStatus, onFailure = ::updateSignOutStatus)
    }

    private fun updateSignUpStatus(status: SignUpStatus) {
        _signUpStatus.value = status
    }

    private fun updateSignUpStatus(status: SignUpStatus, error : String) {
        _signUpStatus.value = status
    }

    private fun updateSignInStatus(status: SignInStatus) {
        _signInStatus.value = status
    }

    private fun updateSignInStatus(status: SignInStatus, error : String) {
        _signInStatus.value = status
    }

    private fun updateSignOutStatus(status: SignOutStatus) {
        _signOutStatus.value = status
    }

    private fun updateSignOutStatus(status: SignOutStatus, error : String) {
        _signOutStatus.value = status
    }

    fun resetAuthStatus() {
        _authStatus.value = null
    }

    fun resetSignOutStatus() {
        _signOutStatus.value = null
    }
}