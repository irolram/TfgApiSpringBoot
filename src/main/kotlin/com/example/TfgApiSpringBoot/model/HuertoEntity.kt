package com.example.TfgApiSpringBoot.model


import com.fasterxml.jackson.annotation.JsonBackReference
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
    var creadorId: String = "",
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference // 🚩 "Yo no vuelvo a pintar al usuario para no crear un bucle"
    val usuario: UsuarioEntity? = null
)