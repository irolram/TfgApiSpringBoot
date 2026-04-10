package com.example.TfgApiSpringBoot.security

import com.example.TfgApiSpringBoot.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            try {
                if (jwtUtil.validateToken(token)) {
                    val userId = jwtUtil.extractUserId(token)
                    val role = jwtUtil.extractRole(token)

                    println("DEBUG SECURITY: Intentando acceder Usuario: $userId con ROL: $role")
                    val authorities = listOf(SimpleGrantedAuthority("ROLE_${role.uppercase()}"))
                    val authToken = UsernamePasswordAuthenticationToken(userId, null, authorities)

                    SecurityContextHolder.getContext().authentication = authToken
                }
            } catch (e: Exception) {
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }
}