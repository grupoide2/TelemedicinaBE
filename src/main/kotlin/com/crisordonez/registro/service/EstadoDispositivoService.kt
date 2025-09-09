package com.crisordonez.registro.service

import com.crisordonez.registro.model.dtos.EstadoDispositivoDto
import com.crisordonez.registro.repository.EstadoDispositivoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EstadoDispositivoService(
    private val estadoDispositivoRepository: EstadoDispositivoRepository
) {

    fun getEstadoDispositivos(
        fechaInicio: LocalDateTime?,
        fechaFin: LocalDateTime?,
        page: Int,
        size: Int
    ): Page<EstadoDispositivoDto> {
        val pageable = PageRequest.of(page, size)
        return getEstadoDispositivos(fechaInicio, fechaFin, pageable)
    }

    fun getEstadoDispositivos(
        fechaInicio: LocalDateTime?,
        fechaFin: LocalDateTime?,
        pageable: Pageable
    ): Page<EstadoDispositivoDto> {
        return if (fechaInicio != null && fechaFin != null) {
            estadoDispositivoRepository.findEstadoDispositivosConFiltro(fechaInicio, fechaFin, pageable)
        } else {
            estadoDispositivoRepository.findEstadoDispositivosSinFiltro(pageable)
        }
    }
}
