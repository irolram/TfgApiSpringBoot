package com.example.TfgApiSpringBoot.service

import com.example.TfgApiSpringBoot.model.Rol
import com.example.TfgApiSpringBoot.model.UsuarioEntity
import com.example.TfgApiSpringBoot.repository.IUsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService(private val IUsuarioRepository: IUsuarioRepository) {

    // Buscar por ID (UID de Firebase)
    fun obtenerPorId(id: String): UsuarioEntity? {
        return IUsuarioRepository.findById(id).orElse(null)
    }

    // El método de "Auto-registro" que vimos antes
    @Transactional
    fun asegurarUsuario(userId: String, email: String): UsuarioEntity {
        val existente = obtenerPorId(userId)

        return if (existente != null) {
            existente
        } else {
            val nuevo = UsuarioEntity(
                id = userId,
                email = email,
                nombre = "Usuario",
                apellidos = "EcoDrop",
                rol = Rol.USER
            )
            IUsuarioRepository.save(nuevo)
        }
    }
}