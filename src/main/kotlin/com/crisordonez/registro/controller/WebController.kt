package com.crisordonez.registro.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {

    @GetMapping("/web")
    fun serveFrontend(): String {
        return "redirect:/web/index.html"
    }
}


