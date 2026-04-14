package com.example.TfgApiSpringBoot.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*



@Entity
@Table(name = "usuarios")
class UsuarioEntity(
    @Id
    @Column(unique = true, nullable = false)
    val id: String = "",

    var nombre: String = "",
    var apellidos: String = "",

    @Column(unique = true)
    var email: String = "",

    @Enumerated(EnumType.STRING)
    var rol: Rol = Rol.USER,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    val usuario: UsuarioEntity? = null
)