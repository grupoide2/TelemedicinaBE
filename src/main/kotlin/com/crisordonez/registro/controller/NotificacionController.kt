package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.NotificacionProgramadaRequest
import com.crisordonez.registro.model.requests.NotificacionRequest
import com.crisordonez.registro.model.responses.NotificacionResponse
import com.crisordonez.registro.service.NotificacionServiceInterface
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/notificaciones")
class NotificationController(
    private val notificacionService: NotificacionServiceInterface
) {

    /**
     * Crea una notificación puntual (no programada).
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crearNotificacion(@Valid @RequestBody request: NotificacionRequest): NotificacionResponse {
        return notificacionService.crearNotificacion(request)
    }
    
    /**
     * Historial de notificaciones de un paciente usando su publicId
     */
    @GetMapping("/{cuentaUsuarioPublicId}")
    fun obtenerHistorial(@PathVariable cuentaUsuarioPublicId: UUID): List<NotificacionResponse> {
        return notificacionService.obtenerHistorialNotificaciones(cuentaUsuarioPublicId)
    }

    @PutMapping("/{notificacionPublicId}/marcar-leida")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun marcarComoLeida(@PathVariable notificacionPublicId: UUID) {
        notificacionService.marcarNotificacionComoLeida(notificacionPublicId)
    }

    @PutMapping("/programada/desactivar-entrega/{cuentaUsuarioPublicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun desactivarRecordatorioEntregaDispositivo(@PathVariable cuentaUsuarioPublicId: UUID) {
        notificacionService.desactivarRecordatorioNoEntregaDispositivo(cuentaUsuarioPublicId)
    }



}