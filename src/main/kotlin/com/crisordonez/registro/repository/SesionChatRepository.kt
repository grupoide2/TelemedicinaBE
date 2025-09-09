package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.SesionChatEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.UUID

interface SesionChatRepository: CrudRepository<SesionChatEntity, Long> {

    fun findByPacienteCuentaPublicId(publicId: UUID): List<SesionChatEntity>

    fun findByPublicId(publicId: UUID): Optional<SesionChatEntity>

    fun findByExamenVphDispositivo(dispositivo: String): SesionChatEntity?

}