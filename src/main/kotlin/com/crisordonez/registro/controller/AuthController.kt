// src/main/kotlin/com/crisordonez/registro/controller/AuthController.kt
package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.LoginRequest
import com.crisordonez.registro.model.responses.AuthResponse
import com.crisordonez.registro.service.AdministradorService
import com.crisordonez.registro.service.MedicoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val adminService: AdministradorService,
    private val medicoService: MedicoService
) {

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<AuthResponse> {
        // 1) Intentar como Admin
        adminService.authenticateAndGet(req.usuario, req.contrasena)?.let { adm ->
            return ResponseEntity.ok(
                AuthResponse(
                    publicId = adm.publicId,
                    nombre   = adm.nombre,
                    usuario  = adm.usuario,
                    role     = "ADMIN"
                )
            )
        }
        // 2) Intentar como Médico
        medicoService.authenticateAndGet(req.usuario, req.contrasena)?.let { med ->
            return ResponseEntity.ok(
                AuthResponse(
                    publicId = med.publicId,
                    nombre   = med.nombre,
                    usuario  = med.usuario,
                    role     = "DOCTOR"
                )
            )
        }
        // 3) Credenciales inválidas
        return ResponseEntity.status(401).build()
    }
}
