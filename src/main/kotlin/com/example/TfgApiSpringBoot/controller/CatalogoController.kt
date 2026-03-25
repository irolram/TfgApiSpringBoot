package com.example.TfgApiSpringBoot.controller


import com.example.TfgApiSpringBoot.dto.CatalogoDTO
import com.example.TfgApiSpringBoot.repository.ICatalogoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/catalogo")
class CatalogoController(private val catalogoRepository: ICatalogoRepository) {

    @GetMapping("/buscar")
    fun buscarPlantas(@RequestParam nombre: String): List<CatalogoDTO> {
        return catalogoRepository.findByNombreContainingIgnoreCase(nombre)
            .map { entity ->
                CatalogoDTO(
                    id = entity.id,
                    nombre = entity.nombre,
                    nombreCientifico = entity.nombreCientifico,
                    riego = entity.riego,
                    luzSolar = entity.luzSolar,
                    iconoUrl = entity.iconoUrl
                )
            }
    }
}