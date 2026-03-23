package com.example.TfgApiSpringBoot.dto

data class CultivoDTO(
    val id: String? = null,
    val nombre: String,
    val variedad: String? = null,
    val estado: String = "PLANTADO",
    val fechaPlantacion: Long = System.currentTimeMillis(),
    val icono: String,
    val riego: String? = null,
    val luzSolar: String? = null,
    val huertoId: String
)