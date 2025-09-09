package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.CodigoQREntity
import com.crisordonez.registro.model.responses.CodigoQRStatusResponse
import com.crisordonez.registro.repository.CodigoQRRepository
import com.crisordonez.registro.repository.DispositivoRegistradoRepository
import com.crisordonez.registro.repository.ExamenVphRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class CodigoQRService(
    private val codigoQRRepository: CodigoQRRepository,
    private val dispositivoRegistradoRepository: DispositivoRegistradoRepository,
    private val examenVphRepository: ExamenVphRepository
) {

    fun guardar(codigo: String, fechaExpiracionStr: String): CodigoQREntity {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fechaExpiracion = LocalDate.parse(fechaExpiracionStr, formatter)

        val entidad = CodigoQREntity(
            codigo = codigo,
            fechaExpiracion = fechaExpiracion
        )
        return codigoQRRepository.save(entidad)
    }

    fun obtenerTodosConStatus(): List<CodigoQRStatusResponse> {
        val codigos = codigoQRRepository.findAll()

        return codigos.map { codigo ->
            val estaRegistrado = dispositivoRegistradoRepository.findByDispositivo(codigo.codigo).isPresent
            val examenOpt = examenVphRepository.findByDispositivo(codigo.codigo)

            val status = if (examenOpt.isPresent) {
                val examen = examenOpt.get()
                val tieneCamposLlenos = examen.fechaResultado != null &&
                        !examen.nombre.isNullOrBlank() &&
                        examen.tamano != null &&
                        examen.tipo != null &&
                        !examen.diagnostico.isNullOrBlank()
                if (tieneCamposLlenos) "resultado listo" else "en proceso"
            } else if (estaRegistrado) {
                "registrado"
            } else {
                "generado"
            }

            CodigoQRStatusResponse(
                codigo = codigo.codigo,
                fechaExpiracion = codigo.fechaExpiracion,
                status = status
            )
        }
    }
}
