package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.EvolucionRequest
import com.crisordonez.registro.model.responses.EvolucionResponse
import com.crisordonez.registro.service.EvolucionServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/evolucion")
class EvolucionController {

    @Autowired
    lateinit var evolucionServiceInterface: EvolucionServiceInterface

    @PostMapping("/admin/{publicId}")
    fun crearEvolucion(@PathVariable publicId: String, @Valid @RequestBody evolucion: EvolucionRequest): ResponseEntity<Unit> {
        // Aquí se asume que la fecha se establecerá en el servicio cuando se guarda la evolución
        return ResponseEntity.ok(evolucionServiceInterface.crearEvolucion(publicId, evolucion))
    }

    @GetMapping("/admin/{publicId}")
    fun getEvolucion(@PathVariable publicId: UUID): ResponseEntity<EvolucionResponse> {
        // Obtener la evolución y devolver la respuesta
        val evolucionResponse = evolucionServiceInterface.getEvolucion(publicId)
        return ResponseEntity.ok(evolucionResponse)
    }

    @GetMapping("/admin")
    fun getTodasEvoluciones(): ResponseEntity<List<EvolucionResponse>> {
        // Obtener todas las evoluciones
        return ResponseEntity.ok(evolucionServiceInterface.getTodasEvoluciones())
    }

    @DeleteMapping("/admin/{publicId}")
    fun eliminarEvolucion(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        // Eliminar la evolución por publicId
        return ResponseEntity.ok(evolucionServiceInterface.eliminarEvolucion(publicId))
    }

}
