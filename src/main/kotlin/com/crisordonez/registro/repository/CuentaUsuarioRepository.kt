package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.Optional
import java.util.UUID

interface CuentaUsuarioRepository: CrudRepository<CuentaUsuarioEntity, Long> {

    fun findByNombreUsuario(nombreUsuario: String): Optional<CuentaUsuarioEntity>

    fun findByPublicId(publicId: UUID): Optional<CuentaUsuarioEntity>

    @Query("SELECT c.publicId FROM CuentaUsuarioEntity c WHERE c.id = :id")
    fun findPublicIdById(@Param("id") id: Long): UUID?

    fun findByTiempoUsoChatIsNotNull(): List<CuentaUsuarioEntity>

}