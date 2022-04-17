package com.example.taskmanager.domain.model

data class User(
    val username: String,
    val email: String,
    val password: String
)

enum class AuthStatus {
    CHECKING_SESSION,
    USER_SIGNED_IN,
    USER_NOT_SIGNED_IN
}

enum class SignOutStatus {
    SIGNING_OUT,
    SIGNED_OUT,
    SIGN_OUT_FAILED
}

enum class SignUpStatus {
    SIGN_UP_STARTED,
    SIGN_UP_SUCCESS,
    SIGN_UP_FAILURE,
    VERIFY_STARTED,
    VERIFY_SUCCESS,
    VERIFY_FAILURE
}

enum class SignInStatus {
    SIGN_IN_STARTED,
    SIGN_IN_SUCCESS,
    SIGN_IN_FAILURE
}