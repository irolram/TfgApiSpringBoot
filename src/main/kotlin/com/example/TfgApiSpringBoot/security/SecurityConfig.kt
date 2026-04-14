package com.example.TfgApiSpringBoot.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtAuthenticationFilter: JwtAuthenticationFilter) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) } // Configuración de CORS corregida
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                // 1. RUTAS PÚBLICAS (Cualquiera entra)
                auth.requestMatchers("/api/auth/**").permitAll()
                auth.requestMatchers("/error").permitAll()
                auth.requestMatchers(HttpMethod.GET, "/api/catalogo/**").permitAll()

                // 2. TICKETS (Permitir POST a USER, MOD y ADMIN)
                // 🚩 Esta regla debe ir ANTES de anyRequest()
                auth.requestMatchers(HttpMethod.POST, "/api/tickets/**").hasAnyRole("USER", "MOD", "ADMIN")
                auth.requestMatchers("/api/tickets/**").hasAnyRole("MOD", "ADMIN")

                // 3. CATÁLOGO (Escritura solo MOD/ADMIN)
                auth.requestMatchers(HttpMethod.POST, "/api/catalogo/**").hasAnyRole("ADMIN", "MOD")
                auth.requestMatchers(HttpMethod.PUT, "/api/catalogo/**").hasAnyRole("ADMIN", "MOD")
                auth.requestMatchers(HttpMethod.DELETE, "/api/catalogo/**").hasAnyRole("ADMIN", "MOD")

                // 4. USUARIOS (Gestión)
                // Primero lo específico (el ID propio)
                auth.requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").authenticated()
                auth.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").authenticated()
                // Luego lo general (listar todos los usuarios para el panel)
                auth.requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("ADMIN", "MOD")
                auth.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}/rol").hasAnyRole("ADMIN", "MOD")
                auth.requestMatchers("/api/huertos/**").hasAnyRole("USER", "MOD", "ADMIN")
                // 5. EL COMODÍN FINAL (Cualquier otra cosa como Huertos o Cultivos)
                // 🚩 SIEMPRE al final del bloque
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // En producción pon tu URL de Railway
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}