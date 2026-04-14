package com.example.TfgApiSpringBoot.repository

import com.example.TfgApiSpringBoot.model.EstadoTicket
import com.example.TfgApiSpringBoot.model.TicketEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ITicketRepository : JpaRepository<TicketEntity, String> {
    // Para que el MOD vea los tickets más nuevos primero
    fun findByEstadoOrderByFechaCreacionDesc(estado: EstadoTicket): List<TicketEntity>

    // Por si quieres que un usuario vea sus propios tickets enviados
    fun findByUsuarioId(usuarioId: String): List<TicketEntity>
}