package com.example.TfgApiSpringBoot.model


import jakarta.persistence.*

@Entity
@Table(name = "huertos")
class HuertoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    var nombre: String = "",
    var descripcion: String = "",
    var latitud: Double = 0.0,
    var longitud: Double = 0.0,
    var imagenUrl: String = "",
    val fechaCreacion: Long = System.currentTimeMillis(),
    var creadorId: String = ""
)