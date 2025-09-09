package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.InformacionSocioeconomicaRequest
import com.crisordonez.registro.model.responses.InformacionSocioeconomicaResponse
import com.crisordonez.registro.service.InformacionSocioeconomicaServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/info-socioeconomica")
class InformacionSocioeconomicaController {

    @Autowired
    lateinit var informacionSocioeconomicaServiceInterface: InformacionSocioeconomicaServiceInterface

    @PutMapping("/editar/{publicId}")
    fun editarInformaion(@PathVariable publicId: UUID, @Valid @RequestBody informacion: InformacionSocioeconomicaRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(informacionSocioeconomicaServiceInterface.editarInfoSocioeconomica(publicId, informacion))
    }

    @GetMapping("/usuario/{publicId}")
    fun getInformacion(@PathVariable publicId: UUID): ResponseEntity<InformacionSocioeconomicaResponse?> {
        return ResponseEntity.ok(informacionSocioeconomicaServiceInterface.getInfoSocioeconomica(publicId))
    }

    @GetMapping("/admin")
    fun getTodosInformacion(): ResponseEntity<List<InformacionSocioeconomicaResponse>> {
        return ResponseEntity.ok(informacionSocioeconomicaServiceInterface.getTodosInfo())
    }
    @GetMapping("/ficha/existe/{cuentaUsuarioPublicId}")
    fun verificarFicha(@PathVariable cuentaUsuarioPublicId: UUID): Map<String, Boolean> {
        val existe = informacionSocioeconomicaServiceInterface.existeFichaSocioeconomica(cuentaUsuarioPublicId)
        return mapOf("existeFicha" to existe)
    }


}