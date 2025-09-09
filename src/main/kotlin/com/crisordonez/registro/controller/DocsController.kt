package com.crisordonez.registro.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

// NO mezcles con el mapping anterior "{*path}". Elimina ese.
@Controller
class DocsController {

    @GetMapping("/docs", "/docs/")
    fun docsRoot(): String = "forward:/docs/index.html"

    // Rutas bajo /docs SIN punto (AntPathMatcher permite este patrón con regex)
    @GetMapping(
        value = [
            "/docs/{path:[^\\.]*}",    // /docs/foo
            "/docs/**/{path:[^\\.]*}"  // /docs/a/b/c
        ]
    )
    fun docsSpa(): String = "forward:/docs/index.html"
}

