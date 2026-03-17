package com.example.TfgApiSpringBoot.dto

data class UsuarioDTO(
    val id: String,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val rol: String
)