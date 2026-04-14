package com.example.TfgApiSpringBoot.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
                // 1. Rutas públicas
                auth.requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/error").permitAll()

                    // 2. Permisos del Catálogo (Nivel Pro)
                    // Cualquiera (User, Técnico, Admin) puede VER las plantas
                    .requestMatchers(HttpMethod.GET, "/api/catalogo/**").permitAll()

                    // SOLO Admin y Técnico pueden CREAR, EDITAR o BORRAR plantas
                    .requestMatchers(HttpMethod.POST, "/api/catalogo/**").hasAnyRole("ADMIN", "MOD")
                    .requestMatchers(HttpMethod.PUT, "/api/catalogo/**").hasAnyRole("ADMIN", "MOD")
                    .requestMatchers(HttpMethod.DELETE, "/api/catalogo/**").hasAnyRole("ADMIN", "MOD")

                    // 3. Permisos de Administración de Usuarios
                    // SOLO el Admin puede gestionar usuarios o ver logs
                    auth.requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").authenticated()
                    auth.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("ADMIN", "MOD")

                    // 4. El resto (Huertos, Cultivos personales) requiere estar logueado
                    .anyRequest().authenticated()
            }
            // Añadimos nuestro filtro personalizado antes del filtro por defecto de Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
    @Bean
    fun corsConfigurationSource(): org.springframework.web.cors.CorsConfigurationSource {
        val configuration = org.springframework.web.cors.CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        val source = org.springframework.web.cors.UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}