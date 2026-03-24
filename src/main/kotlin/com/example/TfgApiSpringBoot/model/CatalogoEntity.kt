package com.example.TfgApiSpringBoot.model


import jakarta.persistence.*

@Entity
@Table(name = "catalogo_plantas")
class CatalogoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nombre: String = "",
    val nombreCientifico: String? = null,
    val riego: String? = null,
    val luzSolar: String? = null,
    val iconoUrl: String? = null
)
