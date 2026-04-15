package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.repository.IHuertoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


    @RestController
    @RequestMapping("/api/admin/stats")
    class AdminController(private val huertoRepository: IHuertoRepository) {

        @GetMapping("/proximidad")
        fun obtenerConteoProximidad(
            @RequestParam lat: Double,
            @RequestParam lng: Double,
            @RequestParam radio: Double
        ): ResponseEntity<Long> {
            // Importante para ver en Railway qué está llegando
            println("Petición Admin: Lat=$lat, Lng=$lng, Radio=$radio")
            val conteo = huertoRepository.contarHuertosEnRadio(lat, lng, radio)
            return ResponseEntity.ok(conteo)
        }
    }