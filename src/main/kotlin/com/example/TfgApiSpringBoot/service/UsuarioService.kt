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


    @Transactional
    fun gestionarCambioDeRol(idObjetivo: String, nuevoRol: Rol, emailEjecutor: String) {
        // 1. Buscamos al que manda (Ejecutor)
        val ejecutor = IUsuarioRepository.findByEmail(emailEjecutor)
            .orElseThrow { Exception("No se encuentra el administrador ejecutor") }

        // 2. Buscamos al que va a recibir el cambio (Objetivo)
        val objetivo = IUsuarioRepository.findById(idObjetivo)
            .orElseThrow { Exception("Usuario a modificar no encontrado") }

        // --- LÓGICA DE JERARQUÍA ---

        when (ejecutor.rol) {
            Rol.MOD -> {
                // REGLA 1: Un MOD no puede tocar a un ADMIN u otro MOD
                if (objetivo.rol == Rol.ADMIN || objetivo.rol == Rol.MOD) {
                    throw IllegalAccessException("Como Moderador no puedes modificar a rangos iguales o superiores")
                }

                // REGLA 2: Un MOD solo puede subir a USER -> MOD
                // No puede bajar de MOD -> USER (la regla que me pediste)
                if (objetivo.rol == Rol.USER && nuevoRol == Rol.MOD) {
                    objetivo.rol = Rol.MOD
                } else {
                    throw IllegalAccessException("Un Moderador no tiene permiso para degradar usuarios o asignar el rango ADMIN")
                }
            }

            Rol.ADMIN -> {
                objetivo.rol = nuevoRol
            }

            else -> throw IllegalAccessException("No tienes permisos de gestión")
        }

        // Guardamos los cambios
        IUsuarioRepository.save(objetivo)
    }
}
