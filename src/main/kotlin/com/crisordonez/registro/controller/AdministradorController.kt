// src/main/kotlin/com/crisordonez/registro/controller/AdministradorController.kt
package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.CreateUserRequest
import com.crisordonez.registro.model.requests.UpdateUserRequest
import com.crisordonez.registro.model.requests.LoginRequest
import com.crisordonez.registro.model.responses.AuthResponse
import com.crisordonez.registro.model.entities.Administrador
import com.crisordonez.registro.service.AdministradorService
import com.crisordonez.registro.service.MedicoService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
    @CrossOrigin(
        origins = ["*"],
        allowedHeaders = ["*"],
        methods = [
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.PUT,
            RequestMethod.DELETE,
            RequestMethod.OPTIONS
        ]
    )
    @RestController
    @RequestMapping("/api")
    class AdministradorController(
        private val service: AdministradorService,
        private val medicoService: MedicoService
    ) {


        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/users")
        fun createUser(@RequestBody req: CreateUserRequest): ResponseEntity<Administrador> {
        val created = service.create(req.nombre, req.usuario, req.contrasena)
        return ResponseEntity.status(201).body(created)
        }

        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/users")
        fun listUsers(): List<Administrador> = service.listAll()

        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/users/{id}")
        fun getUser(@PathVariable id: String): ResponseEntity<Administrador> =
            service.getById(id)?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/users/{id}")
        fun updateUser(
            @PathVariable id: String,
            @RequestBody req: UpdateUserRequest
        ): ResponseEntity<Administrador> =
            service.update(id, req.nombre, req.usuario, req.contrasena)?.let {
                ResponseEntity.ok(it)
            } ?: ResponseEntity.notFound().build()

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/users/{id}")
        fun deleteUser(@PathVariable id: String): ResponseEntity<Void> =
            if (service.delete(id)) ResponseEntity.noContent().build()
            else ResponseEntity.notFound().build()


    }
