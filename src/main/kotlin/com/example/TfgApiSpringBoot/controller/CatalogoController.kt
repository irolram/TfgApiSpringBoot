package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.dto.CatalogoDTO
import com.example.TfgApiSpringBoot.repository.ICatalogoRepository
import org.hibernate.query.results.Builders.entity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/catalogo")
class CatalogoController(private val catalogoRepository: ICatalogoRepository) {
    @GetMapping("/buscar")
    fun buscarPlantas(@RequestParam nombre: String): ResponseEntity<List<CatalogoDTO>> {
        val plantas = catalogoRepository.findByNombreContainingIgnoreCase(nombre)

        if (plantas.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val listaDto = plantas.map { entity ->
            CatalogoDTO(
                id = entity.id,
                nombreCientifico = entity.nombreCientifico,
                nombre = entity.nombre,
                instrucciones = entity.instrucciones,
                temporadaIdeal = entity.temporadaIdeal,
                profundidadSiembra = entity.profundidadSiembra,
                distanciaEntrePlantas = entity.distanciaEntrePlantas,
                icono = entity.icono,
                riego = entity.riego,
                luzSolar = entity.luzSolar,
                diasCrecimiento = entity.diasCrecimiento
            )
        }

        return ResponseEntity.ok(listaDto)
    }

    @GetMapping
    fun obtenerTodoElCatalogo(): ResponseEntity<List<CatalogoDTO>> {

        val lista = catalogoRepository.findAll().map { cat ->
            CatalogoDTO(
                id = cat.id,
                nombreCientifico = cat.nombreCientifico,
                nombre = cat.nombre,
                instrucciones = cat.instrucciones,
                temporadaIdeal = cat.temporadaIdeal,
                profundidadSiembra = cat.profundidadSiembra,
                distanciaEntrePlantas = cat.distanciaEntrePlantas,
                icono = cat.icono,
                riego = cat.riego,
                luzSolar = cat.luzSolar,
                diasCrecimiento = cat.diasCrecimiento
            )
        }
        
        return ResponseEntity.ok(lista)
    }
}