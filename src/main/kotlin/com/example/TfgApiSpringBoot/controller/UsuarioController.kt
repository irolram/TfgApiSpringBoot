package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.model.UsuarioEntity
import com.example.TfgApiSpringBoot.repository.UsuarioRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios") // Esta es la ruta base
class UsuarioController(private val usuarioRepository: UsuarioRepository) {

    @PostMapping // Esto responde al @POST de tu Retrofit en Android
    fun registrarUsuario(@RequestBody usuario: UsuarioEntity): UsuarioEntity {
        // Guardamos el usuario que viene de la App directamente en MySQL
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
}