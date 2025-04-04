package com.example.academia.model

data class User(
    val id: Long? = null,
    val username: String,
    val password: String,
    val email: String,
    val rol: String
)
