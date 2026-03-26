package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.dto.CatalogoDTO
import com.example.TfgApiSpringBoot.repository.ICatalogoRepository
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
}