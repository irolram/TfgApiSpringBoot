package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.dto.HuertoDTO
import com.example.TfgApiSpringBoot.model.HuertoEntity
import com.example.TfgApiSpringBoot.repository.HuertoRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/huertos")
class HuertoController(private val huertoRepository: HuertoRepository) {

    @PostMapping
    fun crearHuerto(
        @RequestBody dto: HuertoDTO,
        authentication: org.springframework.security.core.Authentication // 🔌 Inyectado directamente
    ): ResponseEntity<HuertoDTO> {

        // Aquí Kotlin ya sabe que 'authentication' no es nulo si la ruta está protegida
        val userIdFromToken = authentication.name

        val entity = HuertoEntity(
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            latitud = dto.latitud ?: 0.0,
            longitud = dto.longitud ?: 0.0,
            imagenUrl = dto.imagenUrl ?: "",
            creadorId = userIdFromToken
        )

        val guardado = huertoRepository.save(entity)
        return ResponseEntity.ok(toDTO(guardado))
    }

    @GetMapping
    fun obtenerTodos(): ResponseEntity<List<HuertoDTO>> {
        // 🔍 Opcional: Si solo quieres ver TUS huertos y no los de todo el mundo:
        // val auth = SecurityContextHolder.getContext().authentication
        // val listaDTOs = huertoRepository.findByCreadorId(auth.name).map { toDTO(it) }

        val listaDTOs = huertoRepository.findAll().map { toDTO(it) }
        return ResponseEntity.ok(listaDTOs)
    }

    private fun toDTO(entity: HuertoEntity): HuertoDTO = HuertoDTO(
        id = entity.id,
        nombre = entity.nombre,
        descripcion = entity.descripcion,
        latitud = entity.latitud,
        longitud = entity.longitud,
        imagenUrl = entity.imagenUrl,
        fechaCreacion = entity.fechaCreacion,
        creadorId = entity.creadorId
    )
}