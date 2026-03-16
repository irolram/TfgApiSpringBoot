package com.example.TfgApiSpringBoot.dto

data class HuertoDTO(
    val id: String? = null,
    val nombre: String,
    val descripcion: String,
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val imagenUrl: String? = null,
    val fechaCreacion: Long? = null,
    val creadorId: String? = null
)