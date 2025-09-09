package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.ExamenResultadoRequest
import com.crisordonez.registro.model.responses.ExamenVphResponse
import com.crisordonez.registro.service.ExamenVphServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/prueba")
class ExamenVphController {

    @Autowired
    lateinit var examenVphServiceInterface: ExamenVphServiceInterface

    // devuelve solo el nombre del paciente dado el código de dispositivo
    @GetMapping("/medico/nombre/{codigoDispositivo}")
    fun obtenerNombrePaciente(@PathVariable codigoDispositivo: String): ResponseEntity<String> {
        val nombre = examenVphServiceInterface.obtenerNombrePorCodigo(codigoDispositivo)
        return ResponseEntity.ok(nombre)
    }

    @PutMapping("/admin/resultado/{publicId}")
    fun establecerResultado(
        @PathVariable publicId: String,
        @Valid @RequestBody prueba: ExamenResultadoRequest
        ): ResponseEntity<Unit> {
        examenVphServiceInterface.establecerResultadoPrueba(publicId, prueba)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/admin/{publicId}")
    fun getPrueba(@PathVariable publicId: String): ResponseEntity<ExamenVphResponse> {
        return ResponseEntity.ok(examenVphServiceInterface.getPrueba(publicId))
    }

    @GetMapping("/admin")
    fun getTodasPruebas(): ResponseEntity<List<ExamenVphResponse>> {
        return ResponseEntity.ok(examenVphServiceInterface.getTodasPruebas())
    }

    @PostMapping("/medico/subir")
    fun subirExamenSinPacienteId(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("nombre") nombre: String,
        @RequestParam("dispositivo") dispositivo: String,
        @RequestParam("diagnostico") diagnostico: String,
        @RequestParam("genotipos", required = false) genotiposStr: String?
    ): ResponseEntity<String> {
        examenVphServiceInterface.subirResultadoPdf(
            archivo = file,
            nombre = nombre,
            dispositivo = dispositivo,
            diagnostico = diagnostico,
            genotiposStr = genotiposStr
        )
        return ResponseEntity.ok("Examen subido correctamente")
    }
    //  limpia solo los campos de contenido, fechaResultado, nombre, tamano, tipo y diagnostico
    @PatchMapping("/medico/clear-fields/{codigoDispositivo}")
    fun clearExamenFields(@PathVariable codigoDispositivo: String): ResponseEntity<Void> {
        examenVphServiceInterface.clearExamenFields(codigoDispositivo)
        return ResponseEntity.ok().build()
    }
    @GetMapping("/medico/prefixes")
    fun getDevicePrefixes(): ResponseEntity<List<String>> {
        val prefixes = examenVphServiceInterface.getDevicePrefixes()
        return ResponseEntity.ok(prefixes)
    }
}
