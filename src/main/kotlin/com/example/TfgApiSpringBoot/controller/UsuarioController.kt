package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.model.UsuarioEntity
import com.example.TfgApiSpringBoot.repository.IUsuarioRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios") // Esta es la ruta base
class UsuarioController(private val IUsuarioRepository: IUsuarioRepository) {

    @PostMapping // Esto responde al @POST de tu Retrofit en Android
    fun registrarUsuario(@RequestBody usuario: UsuarioEntity): UsuarioEntity {
        // Guardamos el usuario que viene de la App directamente en MySQL
        return IUsuarioRepository.save(usuario)
    }

    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: String): UsuarioEntity? {
        return IUsuarioRepository.findById(id).orElse(null)
    }
    @GetMapping
    fun listarUsuarios(): List<UsuarioEntity> {
        return IUsuarioRepository.findAll()
    }
}