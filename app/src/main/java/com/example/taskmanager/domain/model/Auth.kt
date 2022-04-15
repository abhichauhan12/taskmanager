package com.example.taskmanager.domain.model

data class User(
    val username: String,
    val email: String,
    val password: String
)

enum class AuthStatus {
    LOADING,
    SUCCESS,
    FAILURE
}