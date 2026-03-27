package com.example.TfgApiSpringBoot.dto

import com.fasterxml.jackson.annotation.JsonProperty


data class CatalogoDTO(
    val id: Long?,

    @JsonProperty("nombre_cientifico") 
    val nombreCientifico: String?,
    val nombre: String?,
    val instrucciones: String?,
    @JsonProperty("dias_crecimiento")
    val diasCrecimiento: Int?,
    @JsonProperty("temporada_ideal")
    val temporadaIdeal: String?,
    @JsonProperty("profundidad_siembra")
    val profundidadSiembra: String?,
    @JsonProperty("distancia_entre_plantas")
    val distanciaEntrePlantas: String?,
    @JsonProperty("icono_url")
    val icono: String?,
    val riego: String? = null,
    @JsonProperty("luz_solar")
    val luzSolar: String? = null,
)