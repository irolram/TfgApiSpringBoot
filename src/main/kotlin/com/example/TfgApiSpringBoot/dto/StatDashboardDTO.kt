package com.example.TfgApiSpringBoot.dto

data class StatsDashboardDTO(
    val totalUsuarios: Long,
    val totalHuertos: Long,
    val totalPlantas: Long,
    val usuariosPorRol: Map<String, Long>
)