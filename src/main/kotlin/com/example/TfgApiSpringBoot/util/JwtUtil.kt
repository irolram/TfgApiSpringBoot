package com.example.TfgApiSpringBoot.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    @Value("\${JWT_SECRET}")
    private lateinit var secretKey: String
    private val expirationTime = 3600L

    fun generateToken(userId: String, rol: String): String {
        val claims = mapOf("rol" to rol)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun generateRefreshToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 604800000L)) // 7 días
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun extractUserId(token: String): String = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    fun extractRole(token: String): String = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body["role"] as String
}