package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.model.AuthResponse
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
class AuthController(private val jwtUtil: JwtUtil, private val usuarioService: UsuarioService) {

    @PostMapping("/login-app")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        // El servicio se encarga de todo: si no existe, lo crea; si existe, lo trae.
        val usuario = usuarioService.asegurarUsuario(loginRequest.userId, loginRequest.email)

        // Generamos los tokens con los datos reales de la DB
        val token = jwtUtil.generateToken(usuario.id, usuario.rol.name)
        val refresh = jwtUtil.generateRefreshToken(usuario.id)

        return ResponseEntity.ok(AuthResponse(token, refresh, usuario.id, usuario.rol.name))
    }
}