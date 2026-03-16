package com.example.TfgApiSpringBoot.service

import com.example.TfgApiSpringBoot.model.Rol
import com.example.TfgApiSpringBoot.model.Usuario
import com.example.TfgApiSpringBoot.repository.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    // Buscar por ID (UID de Firebase)
    fun obtenerPorId(id: String): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }

    // Buscar por Email (usando el nuevo método del Repo)
    fun obtenerPorEmail(email: String): Usuario? {
        return usuarioRepository.findByEmail(email).orElse(null)
    }

    // Verificar si el email existe antes de registrar
    fun existeEmail(email: String): Boolean {
        return usuarioRepository.existsByEmail(email)
    }

    // El método de "Auto-registro" que vimos antes
    @Transactional
    fun asegurarUsuario(userId: String, email: String): Usuario {
        val existente = obtenerPorId(userId)

        return if (existente != null) {
            existente
        } else {
            val nuevo = Usuario(
                id = userId,
                email = email,
                nombre = "Usuario",
                apellidos = "EcoDrop",
                rol = Rol.USER
            )
            usuarioRepository.save(nuevo)
        }
    }
}