package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.model.Usuario
import com.example.TfgApiSpringBoot.repository.UsuarioRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios") // Esta es la ruta base
class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    @PostMapping // Esto responde al @POST de tu Retrofit en Android
    fun registrarUsuario(@RequestBody usuario: Usuario): Usuario {
        // Guardamos el usuario que viene de la App directamente en MySQL
        return usuarioRepository.save(usuario)
    }

    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: String): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }
    @GetMapping
    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }
}