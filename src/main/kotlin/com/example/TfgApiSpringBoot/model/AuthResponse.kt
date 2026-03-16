package com.example.TfgApiSpringBoot.model

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val role: String,
    val expiresIn: Long = 3600
)
