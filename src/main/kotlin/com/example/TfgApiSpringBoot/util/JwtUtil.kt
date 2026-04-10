package com.example.TfgApiSpringBoot.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {

    @Value("\${JWT_SECRET}")
    private lateinit var secretKey: String

    private val expirationTime = 3600000L // 1 hora (estaba en segundos, mejor en ms)

    // 🔌 Generamos la llave de forma segura a partir del String del secreto
    private fun getSigningKey(): SecretKey {
        val keyBytes = secretKey.toByteArray(StandardCharsets.UTF_8)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(userId: String, rol: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .claim("rol", rol)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 604800000L)) // 7 días
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    // 🔌 Centralizamos la extracción de Claims para no repetir código
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = extractAllClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun extractUserId(token: String): String = extractAllClaims(token).subject

    fun extractRole(token: String): String {
        val claims = extractAllClaims(token)
        return claims["rol"] as String
    }
}