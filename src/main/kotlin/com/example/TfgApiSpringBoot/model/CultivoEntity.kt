package com.example.TfgApiSpringBoot.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "cultivos")
class CultivoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    var nombre: String = "",

    @Column(length = 50)
    var estado: String = "PLANTADO",

    @Column(name = "fecha_plantacion")
    var fechaPlantacion: Long = System.currentTimeMillis(),

    @Column(name = "huerto_id", nullable = false)
    var huertoId: String = "",
    @Column(name = "catalogo_info")
    var catalogoId: Long? = null,
    @Column(name = "apodo")
    var apodo: String = "",
)