package com.crisordonez.registro.model.requests

import com.crisordonez.registro.model.enums.OpcionesEnum
import com.crisordonez.registro.model.enums.RangoTiempoEnum
import jakarta.validation.constraints.NotNull

data class SaludSexualRequest(

    val estaEmbarazada: Boolean = false,

    @field:NotNull(message = "Fecha de última menstruación es requerida")
    val fechaUltimaMenstruacion: String,

    @field:NotNull(message = "Último examen de PAP es requerido")
    val ultimoExamenPap: RangoTiempoEnum,

    @field:NotNull(message = "Tiempo de prueba de VPH es requerido")
    val tiempoPruebaVph: RangoTiempoEnum,

    @field:NotNull(message = "Número de parejas sexuales es requerido")
    val numParejasSexuales: Int,

    @field:NotNull(message = "Tiene ETS es requerido")
    val tieneEts: OpcionesEnum,

    val nombreEts: String? = null

)
