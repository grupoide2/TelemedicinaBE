package com.crisordonez.registro.controller

import com.crisordonez.registro.model.requests.AppVersionRequest
import com.crisordonez.registro.model.requests.TiempoChatRequest
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import com.crisordonez.registro.service.CuentaUsuarioServiceInterface
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/usuarios")
class CuentaUsuarioController {

    @Autowired
    lateinit var cuentaUsuarioServiceInterface: CuentaUsuarioServiceInterface

    @PostMapping("/registro")
    fun crearCuentaUsuario(@Valid @RequestBody cuenta: CuentaUsuarioRequest): ResponseEntity<CuentaUsuarioResponse> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.crearCuentaUsuario(cuenta))
    }

    @PostMapping("/autenticar")
    fun autenticar(@Valid @RequestBody usuario: CuentaUsuarioRequest): ResponseEntity<CuentaUsuarioResponse> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.autenticar(usuario))
    }

    @GetMapping("/admin/{publicId}")
    fun getUsuario(@PathVariable publicId: UUID): ResponseEntity<CuentaUsuarioResponse> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.getCuentaUsuario(publicId))
    }

    @GetMapping("/admin")
    fun getTodosUsuarios(): ResponseEntity<List<CuentaUsuarioResponse>> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.getAllCuentas())
    }

    @DeleteMapping("/admin/eliminar/{publicId}")
    fun eliminarUsuario(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.eliminarCuentaUsuario(publicId))
    }

    @PutMapping("/editar/{publicId}")
    fun editarUsuario(@PathVariable publicId: UUID, @Valid @RequestBody cuenta: CuentaUsuarioRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.editarCuentaUsuario(publicId, cuenta))
    }

    @GetMapping("/validar")
    fun validarToken(@RequestHeader("token") token: String): ResponseEntity<String?> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.validarExpiracionToken(token))
    }

    @PutMapping("/cambiar-contrasena")
    fun cambiarContrasena(@Valid @RequestBody cuenta: CuentaUsuarioRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.updateContrasena(cuenta))
    }

    @PutMapping("/chat-time/{publicId}")
    fun actualizarTimestampsChat(
        @PathVariable publicId: UUID,
        @Valid @RequestBody tiempoChatRequest: TiempoChatRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.updateTiempoChat(publicId, tiempoChatRequest))
    }

    @GetMapping("/chat-time-average")
    fun obtenerTiempoPromedioChat(): ResponseEntity<Double> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.getChatTimeAvergae())
    }

    @PutMapping("/app-version/{publicId}")
    fun establecerVersionApp(
        @PathVariable publicId: UUID,
        @RequestBody appVersion: AppVersionRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(cuentaUsuarioServiceInterface.stablishAppVersion(publicId, appVersion))
    }

    @GetMapping("/public-indent/{idInterno}")
    fun obtenerPublicIdDesdeIdInterno(@PathVariable idInterno: Long): ResponseEntity<UUID> {
        val publicId = cuentaUsuarioServiceInterface.obtenerPublicIdPorIdInterno(idInterno)
        return ResponseEntity.ok(publicId)
    }
}