package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.dto.StatsDashboardDTO
import com.example.TfgApiSpringBoot.repository.ICatalogoRepository
import com.example.TfgApiSpringBoot.repository.IHuertoRepository
import com.example.TfgApiSpringBoot.repository.IUsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/stats")
class StatsController(
    private val usuarioRepo: IUsuarioRepository,
    private val huertoRepo: IHuertoRepository,
    private val plantaRepo: ICatalogoRepository
) {
    @GetMapping
    fun getStats(): ResponseEntity<StatsDashboardDTO> {
        val stats = StatsDashboardDTO(
            totalUsuarios = usuarioRepo.count(),
            totalHuertos = huertoRepo.count(),
            totalPlantas = plantaRepo.count(),
            usuariosPorRol = usuarioRepo.findAll()
                .groupBy { it.rol.name }
                .mapValues { it.value.size.toLong() }
        )
        return ResponseEntity.ok(stats)
    }
}