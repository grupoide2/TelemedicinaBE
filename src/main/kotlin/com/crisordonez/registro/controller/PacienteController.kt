package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.DispositivoRegistradoRequest
import com.crisordonez.registro.model.requests.PacienteRequest
import com.crisordonez.registro.model.responses.PacienteResponse
import com.crisordonez.registro.service.PacienteServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/paciente")
class PacienteController {

    @Autowired
    lateinit var pacienteServiceInterface: PacienteServiceInterface
    // editar paciente
    @PutMapping("/editar/{publicId}")
    fun editarPaciente(@PathVariable publicId: UUID, @Valid @RequestBody paciente: PacienteRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(pacienteServiceInterface.editarPaciente(publicId, paciente))
    }

    @GetMapping("/usuario/{publicId}")
    fun getPaciente(@PathVariable publicId: UUID): ResponseEntity<PacienteResponse> {
        return ResponseEntity.ok(pacienteServiceInterface.getPaciente(publicId))
    }

    @GetMapping("/admin")
    fun getTodosPacientes(): ResponseEntity<List<PacienteResponse>> {
        return ResponseEntity.ok(pacienteServiceInterface.getTodosPacientes())
    }


    //Busca el paciente asociado a un dispositivo registrado.
    @GetMapping("/dispositivo/{codigo}")
    fun getByDispositivo(@PathVariable codigo: String): ResponseEntity<PacienteResponse> {
        val dto = pacienteServiceInterface.findByDispositivo(codigo)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(dto)
    }

    @PutMapping("/registrar-dispositivo/{publicId}")
    fun registrarDispositivo(@PathVariable publicId: UUID, @Valid @RequestBody dispositivo: DispositivoRegistradoRequest): ResponseEntity<String> {
        //Prueba de notificicación  de estímulo
        return ResponseEntity.ok(pacienteServiceInterface.registrarDispositivo(publicId, dispositivo))
    }


}