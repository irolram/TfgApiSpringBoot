package com.example.TfgApiSpringBoot.controller

import com.example.TfgApiSpringBoot.model.EstadoTicket
import com.example.TfgApiSpringBoot.model.TicketEntity
import com.example.TfgApiSpringBoot.repository.ITicketRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tickets")
class TicketController(private val ticketRepository: ITicketRepository) {

    // 1. USUARIO: Enviar un nuevo ticket
    @PostMapping
    fun crearTicket(@RequestBody ticket: TicketEntity): ResponseEntity<TicketEntity> {
        val guardado = ticketRepository.save(ticket)
        return ResponseEntity.ok(guardado)
    }

    // 2. MOD/ADMIN: Listar solo los tickets abiertos
    @GetMapping
    fun listarTicketsPendientes(): List<TicketEntity> {
        return ticketRepository.findByEstadoOrderByFechaDesc(EstadoTicket.ABIERTO)
    }

    @PatchMapping("/{id}/resolver")
    fun resolverTicket(@PathVariable id: String): ResponseEntity<TicketEntity> {
        val ticket = ticketRepository.findById(id).orElseThrow { Exception("Ticket no encontrado") }

        ticket.estado = EstadoTicket.CERRADO // Cambiamos el estado
        val ticketGuardado = ticketRepository.save(ticket)

        return ResponseEntity.ok(ticketGuardado)
    }
}