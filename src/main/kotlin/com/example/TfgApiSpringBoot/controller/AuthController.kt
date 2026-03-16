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
        val usuario = usuarioService.asegurarUsuario(loginRequest.userId, loginRequest.email)

        // 🔌 Aseguramos que el nombre del rol no sea nulo ni vacío
        val nombreRol = usuario.rol?.name ?: "USER"

        // Generamos los tokens con el rol asegurado
        val token = jwtUtil.generateToken(usuario.id, nombreRol)
        val refresh = jwtUtil.generateRefreshToken(usuario.id)

        // Devolvemos la respuesta con el rol asegurado
        return ResponseEntity.ok(AuthResponse(token, refresh, usuario.id, nombreRol))
    }
}