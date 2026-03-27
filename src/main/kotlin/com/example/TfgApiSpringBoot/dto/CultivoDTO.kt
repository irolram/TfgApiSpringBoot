package com.example.TfgApiSpringBoot.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CultivoDTO(
    val id: String? = null,
    val nombre: String,
    val estado: String = "PLANTADO",
    @JsonProperty("fechaPlantacion")
    val fechaPlantacion: Long = System.currentTimeMillis(),
    @JsonProperty("catalogo_info")
    val infoCatalogo: CatalogoDTO?,
    val huertoId: String,
    )