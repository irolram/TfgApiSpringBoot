package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.model.Rol
import com.example.TfgApiSpringBoot.model.UsuarioEntity
import com.example.TfgApiSpringBoot.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    // Listar todos (Para el panel de Admin/Mod)
    @GetMapping
    fun listarUsuarios(): List<UsuarioEntity> {
        // Podrías crear un método en el service, pero para un findAll simple esto vale
        return usuarioService.asegurarListaCompleta()
    }

    // Obtener perfil propio o de otro
    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: String): ResponseEntity<UsuarioEntity> {
        val usuario = usuarioService.obtenerPorId(id)
        return if (usuario != null) ResponseEntity.ok(usuario) else ResponseEntity.notFound().build()
    }

    // Actualizar datos básicos (Nombre, Apellidos)
    @PutMapping("/{uid}")
    fun actualizarUsuario(
        @PathVariable uid: String,
        @RequestBody datosActualizados: UsuarioEntity
    ): ResponseEntity<UsuarioEntity> {
        // Lo ideal sería pasar esta lógica también al Service
        val usuario = usuarioService.obtenerPorId(uid) ?: return ResponseEntity.notFound().build()

        usuario.nombre = datosActualizados.nombre
        usuario.apellidos = datosActualizados.apellidos
        // No dejamos cambiar el email ni el rol por aquí por seguridad

        return ResponseEntity.ok(usuarioService.actualizar(usuario))
    }

    // El endpoint de la discordia: Gestión de Roles
    @PutMapping("/{id}/rol")
    fun actualizarRol(
        @PathVariable id: String,
        @RequestParam nuevoRol: Rol,
        authentication: Authentication
    ): ResponseEntity<String> {
        return try {
            // authentication.name nos da el UID del usuario logueado
            usuarioService.gestionarCambioDeRol(id, nuevoRol, authentication.name)
            ResponseEntity.ok("Rol actualizado correctamente")
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}