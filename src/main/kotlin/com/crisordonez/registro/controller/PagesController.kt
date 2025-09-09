package com.crisordonez.registro.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PagesController {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("active", "home")
        return "index"
    }

    @GetMapping("/proyecto")
    fun proyecto(model: Model): String {
        model.addAttribute("active", "proyecto")
        return "proyecto"
    }

    @GetMapping("/quienes-somos")
    fun quienesSomos(model: Model): String {
        model.addAttribute("active", "qs")
        return "quienes-somos"
    }

    @GetMapping("/contactos")
    fun contactos(model: Model): String {
        model.addAttribute("active", "contactos")
        return "contactos"
    }
    @GetMapping("/preguntas-frecuentes")
    fun preguntasFrecuentes(model: Model): String {
        model.addAttribute("active", "proyecto")
        return "preguntas-frecuentes" // nombre del template sin .html
    }

    @GetMapping("/hl7")
    fun hl7(model: Model): String {
        model.addAttribute("active", "hl7")
        model.addAttribute("fhirEntities", listOf(
            "paciente",
            "medico",
            "sesion-chat",
            "examen-vph",
            "salud-sexual",
            "evolucion",
            "archivo"
        ))
        return "hl7"
    }

}
