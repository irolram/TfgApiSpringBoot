package com.example.TfgApiSpringBoot.dto

data class CultivoDTO(
    val id: String? = null,
    val nombre: String,
    val estado: String = "PLANTADO",
    val fechaPlantacion: Long = System.currentTimeMillis(),
    val infoCatalogo: CatalogoDTO?,
    val huertoId: String,
    )