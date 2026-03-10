package com.example.TfgApiSpringBoot.model


import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    val id: String, // Aquí guardaremos el UID de Firebase que viene de Android

    val nombre: String,
    val apellidos: String,
    val email: String,

    @Column(name = "rol")
    val rol: String = "USER"
)