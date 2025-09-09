package com.crisordonez.registro.model.entities

import com.crisordonez.registro.model.enums.OpcionesEnum
import com.crisordonez.registro.model.enums.RangoTiempoEnum
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "salud_sexual")
data class SaludSexualEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var publicId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var estaEmbarazada: Boolean = false,

    @Column(nullable = false)
    var fechaUltimaMenstruacion: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var ultimoExamenPap: RangoTiempoEnum,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var tiempoPruebaVph: RangoTiempoEnum,

    @Column(nullable = false)
    var numParejasSexuales: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var tieneEts: OpcionesEnum,

    var nombreEts: String? = null,

    @OneToOne
    @JoinColumn(name = "examen_vph_id")
    var examenVph: ExamenVphEntity? = null,

    @OneToOne
    @JoinColumn(name = "paciente_id")
    var paciente: PacienteEntity? = null


) : AuditModel()
