package com.crisordonez.registro.controller

import com.crisordonez.registro.model.responses.SaludSexualResponse
import com.crisordonez.registro.service.SaludSexualServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/salud-sexual")
class SaludSexualController {

    @Autowired
    lateinit var saludSexualServiceInterface: SaludSexualServiceInterface

    @GetMapping("/admin/{publicId}")
    fun getSaludSexual(@PathVariable publicId: UUID): ResponseEntity<SaludSexualResponse> {
        return ResponseEntity.ok(saludSexualServiceInterface.getSaludSexual(publicId))
    }

    @GetMapping("/admin")
    fun getTodoSaludSexual(): ResponseEntity<List<SaludSexualResponse>> {
        return ResponseEntity.ok(saludSexualServiceInterface.getTodosSaludSexual())
    }

}