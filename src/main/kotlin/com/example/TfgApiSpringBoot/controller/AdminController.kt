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
        // Log para que veas en la consola de Railway si llegan bien los datos
        println("Admin Request -> Lat: $lat, Lng: $lng, Radio: $radio m")

        val conteo = huertoRepository.contarHuertosEnRadio(lat, lng, radio)
        return ResponseEntity.ok(conteo)
    }

    // Aquí puedes meter más adelante el método para el Dashboard (total usuarios, etc.)
    @GetMapping
    fun obtenerEstadisticasGenerales(): ResponseEntity<Any> {
        // Ejemplo rápido
        return ResponseEntity.ok(mapOf("mensaje" to "Aquí irán tus gráficas"))
    }
}