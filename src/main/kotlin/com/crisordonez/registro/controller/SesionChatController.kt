package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.SesionChatRequest
import com.crisordonez.registro.model.responses.SesionChatResponse
import com.crisordonez.registro.service.SesionChatServiceInterface
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
@RequestMapping("/sesion-chat")
class SesionChatController {

    @Autowired
    lateinit var sesionChatServiceInterface: SesionChatServiceInterface

    @PostMapping("/usuario")
    fun crearSesionChat(@Valid @RequestBody sesion: SesionChatRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(sesionChatServiceInterface.crearSesionChat(sesion))
    }

    @GetMapping("/usuario/{publicId}")
    fun getSesionChat(@PathVariable publicId: UUID): ResponseEntity<List<SesionChatResponse>> {
        return ResponseEntity.ok(sesionChatServiceInterface.getSesionChat(publicId))
    }

    @GetMapping("/admin")
    fun getTodosSesionChat(): ResponseEntity<List<SesionChatResponse>> {
        return ResponseEntity.ok(sesionChatServiceInterface.getTodosSesionChat())
    }

    @DeleteMapping("/admin/{publicId}")
    fun eliminarSesionChat(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(sesionChatServiceInterface.deleteSesionChat(publicId))
    }

}