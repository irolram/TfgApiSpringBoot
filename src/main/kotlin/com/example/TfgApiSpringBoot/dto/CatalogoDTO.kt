package com.example.TfgApiSpringBoot.dto


data class CatalogoDTO(
    val id: Long?,
    val nombre: String,
    val nombreCientifico: String?,
    val riego: String?,
    val luzSolar: String?,
    val iconoUrl: String?,
    val instrucciones: String?,
    val diasCrecimiento: Int?,
    val temporadaIdeal: String?,
    val profundidadSiembra: String?,
    val distanciaEntrePlantas: String?
)