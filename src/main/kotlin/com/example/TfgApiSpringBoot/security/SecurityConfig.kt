package com.example.TfgApiSpringBoot.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtAuthenticationFilter:  JwtAuthenticationFilter) {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configure(http) }
            .csrf { it.disable() } // Desactivamos CSRF porque JWT ya nos protege
            .sessionManagement {
                // Indicamos que no guardamos sesiones en el servidor (Stateless)
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/api/catalogo/**").permitAll()
                    .anyRequest().authenticated()
            }
            // Añadimos nuestro filtro personalizado antes del filtro por defecto de Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
    @Bean
    fun corsConfigurationSource(): org.springframework.web.cors.CorsConfigurationSource {
        val configuration = org.springframework.web.cors.CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // En producción pondrías tu dominio, para el TFG "*" vale
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        val source = org.springframework.web.cors.UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}