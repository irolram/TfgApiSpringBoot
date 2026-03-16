package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.dto.HuertoDTO
import com.example.TfgApiSpringBoot.model.HuertoEntity
import com.example.TfgApiSpringBoot.repository.HuertoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.collections.map

@RestController
@RequestMapping("/api/huertos")
class HuertoController(private val huertoRepository: HuertoRepository) {

    /**
     * POST: Crea un nuevo huerto
     * Recibe un DTO desde Android, lo guarda como Entity y responde con el DTO creado
     */
    @PostMapping
    fun crearHuerto(@RequestBody dto: HuertoDTO): ResponseEntity<HuertoDTO> {
        // 1. Convertimos el DTO que llega de Android a una Entidad de Base de Datos
        val entity = HuertoEntity(
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            latitud = dto.latitud,
            longitud = dto.longitud,
            imagenUrl = dto.imagenUrl ?: "",
            creadorId = dto.creadorId ?: ""
        )

        // 2. Guardamos en MySQL
        val guardado = huertoRepository.save(entity)

        // 3. Devolvemos el objeto convertido a DTO (ya con su ID generado)
        return ResponseEntity.ok(toDTO(guardado))
    }

    /**
     * GET: Obtiene todos los huertos
     * Devuelve una lista de DTOs para que Android los muestre
     */
    @GetMapping
    fun obtenerTodos(): ResponseEntity<List<HuertoDTO>> {
        val listaDTOs = huertoRepository.findAll().map { entity ->
            toDTO(entity)
        }
        return ResponseEntity.ok(listaDTOs)
    }


    private fun toDTO(entity: HuertoEntity): HuertoDTO {
        return HuertoDTO(
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
}