
// src/main/kotlin/com/crisordonez/registro/controller/DispositivoRegistradoController.kt
package com.crisordonez.registro.controller

import com.crisordonez.registro.model.responses.DispositivoRegistradoResponse
import com.crisordonez.registro.service.DispositivoRegistradoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.NoSuchElementException

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"], methods = [
    RequestMethod.GET, RequestMethod.OPTIONS
])
@RestController
@RequestMapping("/api/dispositivos_registrados")
class DispositivoRegistradoController(
    private val service: DispositivoRegistradoService
) {

    @GetMapping("/{dispositivo}")
    fun getByDispositivo(@PathVariable dispositivo: String)
            : ResponseEntity<DispositivoRegistradoResponse> {
        return try {
            val resp = service.findByDispositivo(dispositivo)
            ResponseEntity.ok(resp)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}