package com.crisordonez.registro.configuration

import com.crisordonez.registro.service.CuentaUsuarioDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    lateinit var cuentaUsuarioDetailService: CuentaUsuarioDetailService

    @Autowired
    lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors { }
            .csrf { it.disable() }
            .authorizeHttpRequests { registry ->
                // para el build web
                registry.requestMatchers("/web/**", "/static/**", "/index.html", "/favicon.png", "/icons/**", "/flutter_bootstrap.js", "/web/flutter_service_worker.js", "/web/main.dart.js", "/favicon.ico", "/assets/**").permitAll();

                registry.requestMatchers(
                    "/docs/**", "/css/**", "/js/**", "/images/**", "/img/**", "/assets/**", "/webjars/**"
                ).permitAll()

                // 1) Permito todos los OPTIONS (CORS preflight)
                registry.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 2) Login y creación de administradores
                registry.requestMatchers("/api/auth/login", "/api/users").permitAll()

                // 3) CRUD de administradores
                registry.requestMatchers(HttpMethod.GET,    "/api/users/**").permitAll()
                registry.requestMatchers(HttpMethod.POST,   "/api/users/**").permitAll()
                registry.requestMatchers(HttpMethod.PUT,    "/api/users/**").permitAll()
                registry.requestMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()

                // 4) Lectura de médicos
                registry.requestMatchers(HttpMethod.GET,    "/api/medicos/**").permitAll()

                // 5) Creación, actualización y eliminación de médicos
                registry.requestMatchers(HttpMethod.POST,   "/api/medicos/**").permitAll()
                registry.requestMatchers(HttpMethod.PUT,    "/api/medicos/**").permitAll()
                registry.requestMatchers(HttpMethod.DELETE, "/api/medicos/**").permitAll()

                // 6) Consultas de códigos QR
                registry.requestMatchers(HttpMethod.GET, "/api/dispositivos_registrados/**").permitAll()

                // 7) Lectura de pacientes
                registry.requestMatchers(HttpMethod.GET, "/api/pacientes/**").permitAll()
                registry.requestMatchers(HttpMethod.GET, "/usuarios/public-indent/*").permitAll()
                registry.requestMatchers(HttpMethod.GET, "/public-indent/*").permitAll()

                // 8) Permitir subida de resultados por médico desde web
                registry.requestMatchers(HttpMethod.POST, "/prueba/medico/subir/**").permitAll()
                registry.requestMatchers(HttpMethod.GET, "/prueba/medico/nombre/**").permitAll()

                // CRUD de examenes
                registry.requestMatchers(HttpMethod.PATCH, "/prueba/medico/clear-fields/**").permitAll()

                // 9) Generación codigos QR
                registry.requestMatchers(HttpMethod.POST, "/api/codigosqr").permitAll()

                // 10) Listar codigos QR
                registry.requestMatchers(HttpMethod.GET, "/api/codigosqr").permitAll()

                // 11) Listar Examen VPH
                registry.requestMatchers(HttpMethod.GET, "/prueba/admin").permitAll()

                // 12) Listar prefijos
                registry.requestMatchers(HttpMethod.GET,  "/prueba/medico/prefixes").permitAll()

                // 13) CRUD de ubicaciones
                registry.requestMatchers(HttpMethod.GET, "/api/ubicaciones/**").permitAll()
                registry.requestMatchers(HttpMethod.POST, "/api/ubicaciones/**").permitAll()
                registry.requestMatchers(HttpMethod.PUT, "/api/ubicaciones/**").permitAll()
                registry.requestMatchers(HttpMethod.DELETE, "/api/ubicaciones/**").permitAll()
                //registry.requestMatchers("/api/ubicaciones/**").hasRole("ADMIN") // Produccion

                // 14) Listar Estados de Dispositivos
                registry.requestMatchers(HttpMethod.GET, "/api/estado-dispositivos/**").permitAll() // Desarrollo
                //registry.requestMatchers(HttpMethod.GET, "/api/estado-dispositivos/**").hasRole("ADMIN") // Produccion


                // APP MOVIL
                registry.requestMatchers("/usuarios/registro", "/usuarios/autenticar", "/**", "/index.html", "/css/**", "/js/**", "/images/**").permitAll()
                registry.requestMatchers("/usuarios/registro", "/usuarios/autenticar", "/archivo/nombre/**", "/usuarios/validar", "/usuarios/cambiar-contrasena", "/usuarios/chat-time-average").permitAll()

                //NOTIFICACIONES
                registry.requestMatchers(HttpMethod.POST, "/notificaciones").permitAll()
                registry.requestMatchers(HttpMethod.POST, "/notificaciones/programadas").permitAll()
                registry.requestMatchers(HttpMethod.GET, "/notificaciones/*").permitAll()
                registry.requestMatchers(HttpMethod.PUT, "/notificaciones/*/marcar-leida").permitAll()
                registry.requestMatchers(HttpMethod.PUT, "/notificaciones/programada/desactivar-entrega/*").permitAll()
                registry.requestMatchers(HttpMethod.GET, "/info-socioeconomica/ficha/existe/*").permitAll()

                //REGISTRO- DISPOSITIVOS CON LA APP
                registry.requestMatchers(HttpMethod.POST, "/dispositivo").permitAll()
                registry.requestMatchers(HttpMethod.GET, "/dispositivo/*").permitAll()

                registry.requestMatchers("/anamnesis/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/usuarios/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/evolucion/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/info-socioeconomica/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/paciente/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/prueba/admin/**").permitAll()
                registry.requestMatchers("/salud-sexual/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/sesion-chat/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/archivo/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/medico/*", "/medico").hasRole("ADMIN")
                registry.requestMatchers("/usuarios/editar/**", "/usuarios/chat-time/**", "/usuarios/app-version/**").hasRole("USER")
                registry.requestMatchers("/info-socioeconomica/editar/**", "/info-socioeconomica/usuario/**").hasRole("USER")
                registry.requestMatchers("/paciente/usuario/**", "/paciente/editar/**", "/registrar-dispositivo/**").hasRole("USER")
                registry.requestMatchers("/sesion-chat/usuario/**").hasRole("USER")

                registry.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }



    @Bean
    fun userDetailService(): UserDetailsService {
        return cuentaUsuarioDetailService
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(cuentaUsuarioDetailService)
        provider.setPasswordEncoder(passwordEncoder())

        return provider
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(authenticationProvider())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {

            allowedOriginPatterns = listOf("*")
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().also { src ->
            src.registerCorsConfiguration("/**", config)
        }
    }



}