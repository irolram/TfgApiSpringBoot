package com.example.TfgApiSpringBoot.model

import jakarta.persistence.*

@Entity
@Table(name = "cultivos")
class CultivoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    var nombre: String = "",

    var variedad: String? = null,

    @Column(length = 50)
    var estado: String = "PLANTADO", // Guardamos el nombre del estado

    @Column(name = "fecha_plantacion")
    var fechaPlantacion: Long = System.currentTimeMillis(),

    var icono: String = "",

    @Column(name = "huerto_id", nullable = false)
    var huertoId: String = ""
)