package com.example.TfgApiSpringBoot.dto


data class CatalogoDTO(
    val id: Long?,
    val nombreCientifico: String?,
    val nombre: String?,
    val instrucciones: String?,
    val diasCrecimiento: Int?,
    val temporadaIdeal: String?,
    val profundidadSiembra: String?,
    val distanciaEntrePlantas: String?,
    val icono: String?,
    val riego: String? = null,
    val luzSolar: String? = null,
)