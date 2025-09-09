package com.crisordonez.registro.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jdk.jfr.Timestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "sesion_chat")
data class SesionChatEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Timestamp
    var inicio: LocalDateTime,

    @Timestamp
    var fin: LocalDateTime,

    @Column(columnDefinition = "TEXT")
    var contenido: String? = null,

    @OneToOne
    @JoinColumn(name = "examen_vph_id")
    var examenVph: ExamenVphEntity? = null,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    var paciente: PacienteEntity

) : AuditModel()
