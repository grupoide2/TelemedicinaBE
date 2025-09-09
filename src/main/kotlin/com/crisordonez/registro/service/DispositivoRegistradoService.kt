// src/main/kotlin/com/crisordonez/registro/service/DispositivoRegistradoService.kt
package com.crisordonez.registro.service

import com.crisordonez.registro.model.responses.DispositivoRegistradoResponse
import com.crisordonez.registro.repository.DispositivoRegistradoRepository
import com.crisordonez.registro.repository.PacienteRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class DispositivoRegistradoService(
    private val dispRepo: DispositivoRegistradoRepository,
    private val pacienteRepo: PacienteRepository
) {

    fun findByDispositivo(codigo: String): DispositivoRegistradoResponse {
        // 1) Busca el registro del dispositivo por su código
        val disp = dispRepo.findByDispositivo(codigo)
            .orElseThrow { NoSuchElementException("Dispositivo '$codigo' no registrado") }

        // 2) A partir del pacienteId del dispositivo, devuelve el nombre del paciente
        val paciente = pacienteRepo.findById(disp.pacienteId)
            .orElseThrow { NoSuchElementException("Paciente id=${disp.pacienteId} no encontrado") }

        return DispositivoRegistradoResponse(
            dispositivo     = disp.dispositivo,
            pacienteId      = disp.pacienteId,
            pacienteNombre  = paciente.nombre,
            publicId        = disp.publicId,
            fechaRegistro   = disp.fechaRegistro
        )
    }

}
