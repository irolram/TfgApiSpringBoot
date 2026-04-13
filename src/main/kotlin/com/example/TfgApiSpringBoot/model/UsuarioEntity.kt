package com.example.TfgApiSpringBoot.model

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

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], orphanRemoval = true)
    val huertos: List<HuertoEntity> = mutableListOf()
)