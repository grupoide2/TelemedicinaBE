package com.crisordonez.registro.controller

import com.crisordonez.registro.model.responses.ArchivoResponse
import com.crisordonez.registro.service.ArchivoServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/archivo")
class ArchivoController {

    @Autowired
    lateinit var archivoServiceInterface: ArchivoServiceInterface

    @PostMapping("/admin")
    fun crearArchivo(@RequestParam("archivo") archivo: MultipartFile): ResponseEntity<Unit> {
        return ResponseEntity.ok(archivoServiceInterface.crearArchivo(archivo))
    }

    @GetMapping("/admin/{publicId}")
    fun getArchivo(@PathVariable publicId: UUID): ResponseEntity<ArchivoResponse> {
        return ResponseEntity.ok(archivoServiceInterface.getArchivo(publicId))
    }

    @GetMapping("/nombre/{nombre}")
    fun getArchivoNombre(@PathVariable nombre: String): ResponseEntity<ArchivoResponse> {
        return ResponseEntity.ok(archivoServiceInterface.getArchivoByNombre(nombre))
    }

    @GetMapping("/admin")
    fun getTodosArchivos(): ResponseEntity<List<ArchivoResponse>> {
        return ResponseEntity.ok(archivoServiceInterface.getTodosArchivos())
    }

    @PutMapping("/admin/editar/{publicId}")
    fun editarArchivo(@PathVariable publicId: UUID, @RequestParam("archivo") archivo: MultipartFile): ResponseEntity<Unit> {
        return ResponseEntity.ok(archivoServiceInterface.editarArchivo(publicId, archivo))
    }

    @DeleteMapping("/admin/eliminar/{publicId}")
    fun eliminarArchivo(@PathVariable publicId: UUID): ResponseEntity<Unit> {
        return ResponseEntity.ok(archivoServiceInterface.eliminarArchivo(publicId))
    }

}