package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.model.Rol
import com.example.TfgApiSpringBoot.model.UsuarioEntity
import com.example.TfgApiSpringBoot.repository.IUsuarioRepository
import com.example.TfgApiSpringBoot.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    private val usuarioRepository: IUsuarioRepository,
    private val usuarioService: UsuarioService
) {

    @PostMapping
    fun registrarUsuario(@RequestBody usuario: UsuarioEntity): UsuarioEntity {
        return usuarioRepository.save(usuario)
    }

    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: String): UsuarioEntity? {
        return usuarioRepository.findById(id).orElse(null)
    }

    @GetMapping
    fun listarUsuarios(): List<UsuarioEntity> {
        return usuarioRepository.findAll()
    }

    @PutMapping("/{uid}")
    fun actualizarUsuario(
        @PathVariable uid: String,
        @RequestBody usuarioActualizado: UsuarioEntity
    ): ResponseEntity<UsuarioEntity> {
        val usuarioExistente = usuarioRepository.findById(uid)

        return if (usuarioExistente.isPresent) {
            val usuario = usuarioExistente.get()
            usuario.nombre = usuarioActualizado.nombre
            usuario.email = usuarioActualizado.email
            usuario.apellidos = usuarioActualizado.apellidos

            val usuarioGuardado = usuarioRepository.save(usuario)
            ResponseEntity.ok(usuarioGuardado)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}/rol")
    fun actualizarRol(
        @PathVariable id: String,
        @RequestParam nuevoRol: Rol,
        authentication: Authentication // Spring Security nos da el email de quien pulsa el botón
    ): ResponseEntity<String> {
        return try {
            // Llamamos al servicio que tiene la lógica de Admin vs Mod
            usuarioService.gestionarCambioDeRol(id, nuevoRol, authentication.name)
            ResponseEntity.ok("Rol actualizado correctamente")
        } catch (e: IllegalAccessException) {
            // Si el Mod intenta bajar a alguien, lanzamos un 403 (Prohibido)
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message)
        } catch (e: Exception) {
            // Cualquier otro error (ej: usuario no encontrado)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}