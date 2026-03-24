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

    var variedad: String? = null,

    @Column(length = 50)
    var estado: String = "PLANTADO",

    @Column(name = "fecha_plantacion")
    var fechaPlantacion: Long = System.currentTimeMillis(),

    @Column(length = 1000)
    var icono: String = "",

    //var riego: String? = null,

    //@Column(name = "luz_solar")
    //var luzSolar: String? = null,

    @Column(name = "huerto_id", nullable = false)
    var huertoId: String = ""
)