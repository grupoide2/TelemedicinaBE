package com.crisordonez.registro.model.responses

import java.util.UUID

data class SesionChatResponse(

    val publicId: UUID,

    val contenido: String?,

    val examenVph: ExamenVphResponse? = null

)
