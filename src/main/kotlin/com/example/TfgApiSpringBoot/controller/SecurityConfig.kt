package com.example.TfgApiSpringBoot.controller


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Desactivamos CSRF para que Android pueda hacer POST
            .authorizeHttpRequests { it.anyRequest().permitAll() } // Permitimos TODO el tráfico
            .formLogin { it.disable() } // Quitamos el formulario que ves en la foto
            .httpBasic { it.disable() } // Quitamos el login básico

        return http.build()
    }
}