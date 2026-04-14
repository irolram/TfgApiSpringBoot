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

    // 3. MOD/ADMIN: Resolver (Cerrar) un ticket
    @PutMapping("/{id}/resolver")
    fun resolverTicket(@PathVariable id: String): ResponseEntity<String> {
        val ticket = ticketRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        ticket.estado = EstadoTicket.CERRADO
        ticketRepository.save(ticket)

        return ResponseEntity.ok("Ticket marcado como resuelto")
    }
}