package com.crisordonez.registro.repository

import com.crisordonez.registro.model.entities.CodigoQREntity
import org.springframework.data.jpa.repository.JpaRepository

interface CodigoQRRepository : JpaRepository<CodigoQREntity, Long>
