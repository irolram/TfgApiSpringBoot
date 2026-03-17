package com.example.TfgApiSpringBoot.dto

data class CultivoDTO(
    val id: String? = null,
    val nombre: String,
    val variedad: String? = null,
    val estado: String, // Lo pasamos como String para evitar líos en el JSON
    val fechaPlantacion: Long,
    val icono: String,
    val huertoId: String // ¡Vital para saber a qué huerto pertenece!
)