package com.example.TfgApiSpringBoot.repository



import com.example.TfgApiSpringBoot.model.Rol
import com.example.TfgApiSpringBoot.model.UsuarioEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface IUsuarioRepository : JpaRepository<UsuarioEntity, String> {

    // 1. Buscar por email (útil para validaciones de registro)
    fun findByEmail(email: String): Optional<UsuarioEntity>

    // 2. Comprobar si un email ya existe (devuelve boolean, muy rápido)
    fun existsByEmail(email: String): Boolean

    // 3. Buscar todos los usuarios que tengan un rol específico (ej: todos los ADMIN)
    fun findByRol(rol: Rol): List<UsuarioEntity>

    // 4. Buscar por nombre o apellido (por si quieres hacer un buscador en el panel admin)
    fun findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
        nombre: String,
        apellidos: String
    ): List<UsuarioEntity>

    // 5. Contar cuántos usuarios hay registrados (para estadísticas del TFG)
    override fun count(): Long
}