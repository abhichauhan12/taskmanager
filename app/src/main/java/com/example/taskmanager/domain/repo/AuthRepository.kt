package com.example.taskmanager.domain.repo

import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.taskmanager.domain.model.SignInStatus
import com.example.taskmanager.domain.model.SignUpStatus
import com.example.taskmanager.domain.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    fun getCurrentLoggedInUser(): AuthUser? = Amplify.Auth.currentUser

    fun getCurrentAuthSession(isUserLoggedIn : (Boolean) -> Unit) {
        Amplify.Auth.fetchAuthSession(
            { isUserLoggedIn(it.isSignedIn) },
            { isUserLoggedIn(false) }
        )
    }

    fun signUp(user : User, onSuccess : (SignUpStatus) -> Unit, onFailure : (SignUpStatus, String) -> Unit) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), user.email)
            .build()

        Amplify.Auth.signUp(
            user.username,
            user.password,
            options,
            { onSuccess(SignUpStatus.SIGN_UP_SUCCESS) },
            { exception -> onFailure(SignUpStatus.VERIFY_SUCCESS, exception.message ?: "Some error occurred") }
        )
    }

    fun login(user : User, onSuccess : (SignInStatus) -> Unit, onFailure : (SignInStatus, String) -> Unit) {

        Amplify.Auth.signIn(
            user.username,
            user.password,
            { onSuccess(SignInStatus.SIGN_IN_SUCCESS) },
            { exception -> onFailure(SignInStatus.SIGN_IN_FAILURE, exception.message ?: "Some error occurred") }
        )
    }

    fun verifyOtp(user: User, code : String, onSuccess: (SignUpStatus) -> Unit, onFailure: (SignUpStatus, String) -> Unit) {
        Amplify.Auth.confirmSignUp(
            user.username, code,
            { result -> if (result.isSignUpComplete) onSuccess(SignUpStatus.VERIFY_SUCCESS) else onFailure(SignUpStatus.VERIFY_FAILURE, "") },
            { onFailure(SignUpStatus.VERIFY_FAILURE, it.message ?: "Some error occurred") }
        )
    }
}

