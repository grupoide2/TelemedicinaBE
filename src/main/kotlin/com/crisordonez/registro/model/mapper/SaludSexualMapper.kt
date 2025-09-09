package com.crisordonez.registro.model.mapper

import com.crisordonez.registro.model.entities.SaludSexualEntity
import com.crisordonez.registro.model.requests.SaludSexualRequest
import com.crisordonez.registro.model.responses.SaludSexualResponse

object SaludSexualMapper {

    fun SaludSexualRequest.toEntity(): SaludSexualEntity {
        return SaludSexualEntity(
            estaEmbarazada = this.estaEmbarazada,
            fechaUltimaMenstruacion = this.fechaUltimaMenstruacion,
            ultimoExamenPap = this.ultimoExamenPap,
            tiempoPruebaVph = this.tiempoPruebaVph,
            numParejasSexuales = this.numParejasSexuales,
            tieneEts = this.tieneEts,
            nombreEts = this.nombreEts
        )
    }

    fun SaludSexualRequest.toEntityUpdated(saludSexual: SaludSexualEntity): SaludSexualEntity {
        saludSexual.estaEmbarazada = this.estaEmbarazada
        saludSexual.fechaUltimaMenstruacion = this.fechaUltimaMenstruacion
        saludSexual.ultimoExamenPap = this.ultimoExamenPap
        saludSexual.tiempoPruebaVph = this.tiempoPruebaVph
        saludSexual.numParejasSexuales = this.numParejasSexuales
        saludSexual.tieneEts = this.tieneEts
        saludSexual.nombreEts = this.nombreEts
        return saludSexual
    }

    fun SaludSexualEntity.toResponse(): SaludSexualResponse {
        return SaludSexualResponse(
            publicId = this.publicId,
            estaEmbarazada = this.estaEmbarazada,
            fechaUltimaMenstruacion = this.fechaUltimaMenstruacion,
            ultimoExamenPap = this.ultimoExamenPap.name,
            tiempoPruebaVph = this.tiempoPruebaVph.name,
            numParejasSexuales = this.numParejasSexuales,
            tieneEts = this.tieneEts.name,
            nombreEts = this.nombreEts
        )
    }
}