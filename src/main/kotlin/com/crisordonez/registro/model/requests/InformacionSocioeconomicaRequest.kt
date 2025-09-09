package com.crisordonez.registro.model.requests

import com.crisordonez.registro.model.enums.IngresoEnum
import com.crisordonez.registro.model.enums.InstruccionEnum
import com.crisordonez.registro.model.enums.OpcionesEnum

data class InformacionSocioeconomicaRequest(

    val instruccion: InstruccionEnum? = null,

    val ingresos: IngresoEnum? = null,

    val trabajoRemunerado: OpcionesEnum? = null,

    val ocupacion: String? = null,

    val recibeBono: OpcionesEnum? = null

)
