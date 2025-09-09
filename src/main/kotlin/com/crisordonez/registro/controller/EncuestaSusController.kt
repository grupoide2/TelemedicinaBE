package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.EncuestaSusRequest
import com.crisordonez.registro.service.EncuestaSusServiceInterface
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/encuesta_sus")
@CrossOrigin
class EncuestaSusController(
    private val encuestaService: EncuestaSusServiceInterface
) {

    @PostMapping
    fun guardarEncuesta(@RequestBody request: EncuestaSusRequest): ResponseEntity<Any> {
        return if (encuestaService.guardarEncuesta(request)) {
            ResponseEntity.ok("Encuesta guardada correctamente")
        } else {
            ResponseEntity.badRequest().body("No se pudo guardar la encuesta")
        }
    }

    @GetMapping("/completada/{cuentaUsuarioId}")
    fun verificarEncuestaCompletada(@PathVariable cuentaUsuarioId: String): ResponseEntity<Boolean> {
        val completada = encuestaService.estaEncuestaCompletada(cuentaUsuarioId)  // Cambio aquí para ser consistente
        return ResponseEntity.ok(completada)
    }
}