package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.model.Huerto
import com.example.TfgApiSpringBoot.repository.HuertoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/huertos")
class HuertoController(private val huertoRepository: HuertoRepository) {

    // 1. EL GET: Para que Android pueda LEER la lista
    @GetMapping
    fun obtenerMisHuertos(): ResponseEntity<List<Huerto>> {
        val huertos = huertoRepository.findAll()
        return ResponseEntity.ok(huertos)
    }

    // 2. EL POST: Para que Android pueda GUARDAR un huerto nuevo
    @PostMapping
    fun crearHuerto(@RequestBody huerto: Huerto): ResponseEntity<Huerto> {
        // Guardamos el objeto en MySQL. Al guardarlo, se le asigna el ID automáticamente.
        val huertoGuardado = huertoRepository.save(huerto)

        // Se lo devolvemos a Android ya completo con su ID
        return ResponseEntity.ok(huertoGuardado)
    }
}