package com.example.taskmanager.domain.repo

import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.taskmanager.domain.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    fun getCurrentLoggedInUser(): AuthUser? = Amplify.Auth.currentUser

    fun signUp(user : User, onSuccess : () -> Unit, onFailure : (String) -> Unit) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), user.email)
            .build()

        Amplify.Auth.signUp(
            user.username,
            user.password,
            options,
            { onSuccess() },
            { exception -> onFailure(exception.message ?: "Some error occurred") }
        )
    }
}

