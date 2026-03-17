package com.example.TfgApiSpringBoot.dto


data class AuthResponseDTO(
    val token: String,
    val refresh: String, // Asegúrate de que en Android se llama igual
    val userId: String,
    val rol: String      // 🔌 ¡La clave! Lo llamamos 'rol' a secas
)