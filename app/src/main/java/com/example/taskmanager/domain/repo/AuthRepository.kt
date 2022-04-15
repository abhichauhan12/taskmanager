package com.example.taskmanager.domain.repo

import com.amplifyframework.auth.AuthUser
import com.amplifyframework.core.Amplify
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    fun getCurrentLoggedInUser(): AuthUser? = Amplify.Auth.currentUser

}