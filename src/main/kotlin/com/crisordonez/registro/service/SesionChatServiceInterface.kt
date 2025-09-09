package com.crisordonez.registro.service

import com.crisordonez.registro.model.requests.SesionChatRequest
import com.crisordonez.registro.model.responses.SesionChatResponse
import java.util.UUID

interface SesionChatServiceInterface {

    fun crearSesionChat(sesion: SesionChatRequest)

    fun getSesionChat(publicId: UUID): List<SesionChatResponse>

    fun getTodosSesionChat(): List<SesionChatResponse>

    fun deleteSesionChat(publicId: UUID)

}