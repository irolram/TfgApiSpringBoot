package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.dto.AuthResponseDTO
import com.example.TfgApiSpringBoot.model.LoginRequest
import com.example.TfgApiSpringBoot.service.UsuarioService
import com.example.TfgApiSpringBoot.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtUtil: JwtUtil,
    private val usuarioService: UsuarioService
) {

    @PostMapping("/login-app")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponseDTO> {
        val usuario = usuarioService.asegurarUsuario(loginRequest.userId, loginRequest.email)


        val rolDelUsuario = usuario.rol.name

        // 3. Generamos los tokens
        val token = jwtUtil.generateToken(usuario.id, rolDelUsuario)
        val refresh = jwtUtil.generateRefreshToken(usuario.id)

        // 4. Devolvemos el DTO con el campo 'rol' bien nombrado
        val response = AuthResponseDTO(
            token = token,
            refresh = refresh,
            userId = usuario.id,
            rol = rolDelUsuario
        )

        return ResponseEntity.ok(response)
    }
}