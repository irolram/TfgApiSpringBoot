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
            .csrf { it.disable() } // Desactivamos CSRF porque JWT ya nos protege
            .sessionManagement {
                // Indicamos que no guardamos sesiones en el servidor (Stateless)
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                // Permitimos el acceso libre al Login y Registro
                auth.requestMatchers("/api/auth/**").permitAll()
                    // El resto de la API requiere estar autenticado
                    .anyRequest().authenticated()
            }
            // Añadimos nuestro filtro personalizado antes del filtro por defecto de Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}