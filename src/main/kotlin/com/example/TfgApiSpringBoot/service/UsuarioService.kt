package com.example.TfgApiSpringBoot.service

import com.example.TfgApiSpringBoot.model.Rol
import com.example.TfgApiSpringBoot.model.UsuarioEntity
import com.example.TfgApiSpringBoot.repository.IUsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService(private val usuarioRepository: IUsuarioRepository) {

    fun obtenerPorId(id: String): UsuarioEntity? {
        return usuarioRepository.findById(id).orElse(null)
    }

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
            usuarioRepository.save(nuevo)
        }
    }

    @Transactional
    fun gestionarCambioDeRol(idObjetivo: String, nuevoRol: Rol, idEjecutor: String) {
        // 🚩 ARREGLADO: Buscamos al ejecutor por ID (findById), no por email
        val ejecutor = usuarioRepository.findById(idEjecutor)
            .orElseThrow { Exception("No se encuentra el administrador ejecutor con ID: $idEjecutor") }

        val objetivo = usuarioRepository.findById(idObjetivo)
            .orElseThrow { Exception("Usuario a modificar no encontrado") }

        // --- LÓGICA DE JERARQUÍA (Tu regla de negocio) ---
        when (ejecutor.rol) {
            Rol.MOD -> {
                // El MOD no puede tocar a ADMIN ni a otros MODs
                if (objetivo.rol == Rol.ADMIN || objetivo.rol == Rol.MOD) {
                    throw IllegalAccessException("Rango insuficiente para modificar a este usuario")
                }

                // El MOD solo puede subir USER -> MOD, nada más
                if (objetivo.rol == Rol.USER && nuevoRol == Rol.MOD) {
                    objetivo.rol = Rol.MOD
                } else {
                    throw IllegalAccessException("Un Moderador no puede degradar usuarios ni asignar rango ADMIN")
                }
            }

            Rol.ADMIN -> {
                // El ADMIN tiene control total
                objetivo.rol = nuevoRol
            }

            else -> throw IllegalAccessException("No tienes permisos de gestión")
        }

        usuarioRepository.save(objetivo)
    }

    fun asegurarListaCompleta(): List<UsuarioEntity> {

        return usuarioRepository.findAll()
    }

    @Transactional
    fun actualizar(usuario: UsuarioEntity): UsuarioEntity {
        return usuarioRepository.save(usuario)
    }
}