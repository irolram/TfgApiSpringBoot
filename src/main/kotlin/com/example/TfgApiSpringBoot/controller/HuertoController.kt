package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.dto.CatalogoDTO
import com.example.TfgApiSpringBoot.dto.CultivoDTO
import com.example.TfgApiSpringBoot.dto.HuertoDTO
import com.example.TfgApiSpringBoot.model.CultivoEntity
import com.example.TfgApiSpringBoot.model.HuertoEntity
import com.example.TfgApiSpringBoot.repository.ICatalogoRepository
import com.example.TfgApiSpringBoot.repository.ICultivoRepository
import com.example.TfgApiSpringBoot.repository.IHuertoRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/huertos")
class HuertoController(private val huertoRepository: IHuertoRepository,
                       private val cultivoRepository: ICultivoRepository, private val catalogoRepository: ICatalogoRepository
) {

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
        val auth = SecurityContextHolder.getContext().authentication
        val listaDTOs = huertoRepository.findByCreadorId(auth.name).map { toDTO(it) }

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

    @DeleteMapping("/{id}")
    fun borrarHuerto(@PathVariable id: String): ResponseEntity<Void> {
        // Comprobamos si el huerto existe antes de intentar borrarlo
        return if (huertoRepository.existsById(id)) {
            huertoRepository.deleteById(id)
            // 204 No Content: Todo ha ido bien, pero no devuelvo ningún JSON de vuelta
            ResponseEntity.noContent().build()
        } else {
            // 404 Not Found: El huerto no existe en la base de datos
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/{huertoId}/cultivos")
    fun aniadirCultivo(
        @PathVariable huertoId: String,
        @RequestBody dto: CultivoDTO
    ): ResponseEntity<Any> {
        val nuevoCultivo = CultivoEntity(
            nombre = dto.nombre,
            estado = dto.estado,
            fechaPlantacion = dto.fechaPlantacion,
            catalogoId = dto.infoCatalogo?.id,
            huertoId = huertoId
        )

        cultivoRepository.save(nuevoCultivo)
        return ResponseEntity.ok().build()
    }
    @GetMapping("/{huertoId}/cultivos")
    fun obtenerCultivosDeUnHuerto(@PathVariable huertoId: String): ResponseEntity<List<CultivoDTO>> {

        val listaCultivos = cultivoRepository.findByHuertoId(huertoId).map { entity ->

            // 🔍 BUSQUEDA CRUZADA: Por cada cultivo, buscamos su "manual" en el catálogo
            val plantaDelCatalogo = entity.catalogoId?.let { id ->
                catalogoRepository.findById(id).orElse(null)
            }

            CultivoDTO(
                id = entity.id,
                nombre = entity.nombre,
                estado = entity.estado,
                fechaPlantacion = entity.fechaPlantacion,
                huertoId = entity.huertoId,
                infoCatalogo = plantaDelCatalogo?.let { cat ->
                    CatalogoDTO(
                        id = cat.id,
                        nombreCientifico = cat.nombreCientifico,
                        nombre = cat.nombre,
                        instrucciones = cat.instrucciones,
                        diasCrecimiento = cat.diasCrecimiento,
                        temporadaIdeal = cat.temporadaIdeal,
                        profundidadSiembra = cat.profundidadSiembra,
                        distanciaEntrePlantas = cat.distanciaEntrePlantas,
                        icono = cat.icono,
                        riego = cat.riego,
                        luzSolar = cat.luzSolar
                    )
                }
            )
        }

        return ResponseEntity.ok(listaCultivos)
    }
    @DeleteMapping("/{huertoId}/cultivos/{cultivoId}")
    fun eliminarCultivo(
        @PathVariable huertoId: String,
        @PathVariable cultivoId: String
    ): ResponseEntity<Void> {
        return if (cultivoRepository.existsById(cultivoId)) {
            cultivoRepository.deleteById(cultivoId)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}