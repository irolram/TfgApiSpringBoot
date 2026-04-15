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
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                // 1. RUTAS PÚBLICAS
                auth.requestMatchers("/api/auth/**", "/error").permitAll()
                auth.requestMatchers(HttpMethod.GET, "/api/catalogo/**").permitAll()

                // 2. 🚩 NUEVA REGLA: ESTADÍSTICAS ADMIN (Añádela aquí)
                auth.requestMatchers("/api/admin/stats/**").hasAnyRole("ADMIN", "MOD")

                // 3. TICKETS
                auth.requestMatchers(HttpMethod.POST, "/api/tickets/**").hasAnyRole("USER", "MOD", "ADMIN")
                auth.requestMatchers("/api/tickets/**").hasAnyRole("MOD", "ADMIN")

                // 4. RESTO DE REGLAS
                auth.requestMatchers("/api/usuarios/**").hasAnyRole("ADMIN", "MOD")
                auth.requestMatchers("/api/huertos/**").hasAnyRole("USER", "MOD", "ADMIN")

                auth.anyRequest().authenticated()
            }
            // 🚩 CLAVE: Añadimos el filtro JWT antes del de usuario/contraseña
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}