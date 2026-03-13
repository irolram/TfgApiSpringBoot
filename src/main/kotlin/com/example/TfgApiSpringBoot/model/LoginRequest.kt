package com.example.TfgApiSpringBoot.model

data class LoginRequest(
    val userId: String,   // El UID que nos da Firebase
    val email: String     // Opcional, por si quieres guardar un registro
)