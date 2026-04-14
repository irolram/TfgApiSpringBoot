package com.example.TfgApiSpringBoot.repository

import com.example.TfgApiSpringBoot.model.EstadoTicket
import com.example.TfgApiSpringBoot.model.TicketEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ITicketRepository : JpaRepository<TicketEntity, String> {
    fun findByEstadoOrderByFechaDesc(estado: EstadoTicket): List<TicketEntity>

    fun findByUsuarioId(usuarioId: String): List<TicketEntity>
}