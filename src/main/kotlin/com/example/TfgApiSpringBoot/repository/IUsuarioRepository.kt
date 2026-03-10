package com.example.TfgApiSpringBoot.repository

import com.example.TfgApiSpringBoot.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IUsuarioRepository : JpaRepository<Usuario, String> {

    // .save(usuario) -> Para insertar o actualizar
    // .findById(id)  -> Para buscar un usuario por su UID de Firebase
    // .findAll()     -> Para listar todos los usuarios
}