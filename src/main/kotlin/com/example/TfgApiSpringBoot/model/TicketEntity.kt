package com.example.TfgApiSpringBoot.model

import jakarta.persistence.*
import java.time.LocalDateTime

enum class TipoTicket { ERROR, SUGERENCIA, OTRO }
enum class EstadoTicket { ABIERTO, CERRADO }

@Entity
@Table(name = "tickets")
class TicketEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    var asunto: String = "",

    @Column(columnDefinition = "TEXT") // Para que puedan escribir parrafadas
    var descripcion: String = "",

    @Enumerated(EnumType.STRING)
    var tipo: TipoTicket = TipoTicket.OTRO,

    @Enumerated(EnumType.STRING)
    var estado: EstadoTicket = EstadoTicket.ABIERTO,

    val fechaCreacion: LocalDateTime = LocalDateTime.now(),

    // Solo guardamos el ID del usuario para mantener la DB limpia
    var usuarioId: String = "",
    var usuarioNombre: String = ""
)