package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.dto.StatDashboardDTO
import com.example.TfgApiSpringBoot.repository.ICatalogoRepository
import com.example.TfgApiSpringBoot.repository.IHuertoRepository
import com.example.TfgApiSpringBoot.repository.IUsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/stats")
class EstadisticasController(
    private val usuarioRepo: IUsuarioRepository,
    private val huertoRepo: IHuertoRepository,
    private val plantaRepo: ICatalogoRepository
) {
    @GetMapping
    fun getStats(): ResponseEntity<StatDashboardDTO> {
        val stats = StatDashboardDTO(
            totalUsuarios = usuarioRepo.count(),
            totalHuertos = huertoRepo.count(),
            totalPlantas = plantaRepo.count(),
            usuariosPorRol = usuarioRepo.findAll()
                .groupBy { it.rol.name }
                .mapValues { it.value.size.toLong() }
        )
        return ResponseEntity.ok(stats)
    }

    @GetMapping("/proximidad")
    fun getHuertosCercanos(
        @RequestParam lat: Double,
        @RequestParam lng: Double,
        @RequestParam radio: Double
    ): ResponseEntity<Long> {
        val conteo = huertoRepo.contarHuertosEnRadio(lat, lng, radio)
        return ResponseEntity.ok(conteo)
    }
}