package com.example.TfgApiSpringBoot.model


import jakarta.persistence.*

@Entity
@Table(name = "catalogo_plantas")
class CatalogoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nombre: String = "",

    @Column(name = "nombre_cientifico")
    val nombreCientifico: String? = null,

    val riego: String? = null,

    @Column(name = "luz_solar")
    val luzSolar: String? = null,

    @Column(name = "icono_url")
    val iconoUrl: String? = null,

    @Column(columnDefinition = "TEXT")
    val instrucciones: String? = null,

    @Column(name = "dias_crecimiento")
    val diasCrecimiento: Int? = null,

    @Column(name = "temporada_ideal")
    val temporadaIdeal: String? = null,

    @Column(name = "profundidad_siembra")
    val profundidadSiembra: String? = null,

    @Column(name = "distancia_entre_plantas")
    val distanciaEntrePlantas: String? = null
)
