package com.example.TfgApiSpringBoot.model


import jakarta.persistence.*

@Entity
@Table(name = "huertos")
data class Huerto(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    // 🔌 AÑADE EL = "" A ESTOS DOS PARA EVITAR EL ERROR DE JACKSON
    val nombre: String = "",
    val descripcion: String = "",

    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val imagenUrl: String = "",
    val fechaCreacion: Long = System.currentTimeMillis(),
    val creadorId: String = ""
)