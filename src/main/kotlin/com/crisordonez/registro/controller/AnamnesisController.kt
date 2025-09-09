package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.AnamnesisRequest
import com.crisordonez.registro.model.responses.AnamnesisResponse
import com.crisordonez.registro.service.AnamnesisServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/anamnesis")
class AnamnesisController {

    @Autowired
    lateinit var anamnesisServiceInterface: AnamnesisServiceInterface

    @PostMapping("/admin/{publicId}")
    fun crearAnamnesis(@PathVariable publicId: UUID, @Valid @RequestBody anamnesis: AnamnesisRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(anamnesisServiceInterface.crearAnamnesis(publicId, anamnesis))
    }

    @GetMapping("/admin/{publicId}")
    fun getAnamnesis(@PathVariable publicId: UUID): ResponseEntity<AnamnesisResponse> {
        return ResponseEntity.ok(anamnesisServiceInterface.getAnamnesis(publicId))
    }

    @GetMapping("/admin")
    fun getTodasAnamnesis(): ResponseEntity<List<AnamnesisResponse>> {
        return ResponseEntity.ok(anamnesisServiceInterface.getTodasAnamnesis())
    }

    @PutMapping("/admin/{publicId}")
    fun editarAnamnesis(@PathVariable publicId: UUID, @Valid @RequestBody anamnesis: AnamnesisRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(anamnesisServiceInterface.editarAnamnesis(publicId, anamnesis))
    }

    @DeleteMapping("/admin/{publicId}")
    fun eliminarAnamnesis(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(anamnesisServiceInterface.eliminarAnamnesis(publicId))
    }

}