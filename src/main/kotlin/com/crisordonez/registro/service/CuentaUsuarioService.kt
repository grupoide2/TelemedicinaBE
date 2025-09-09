package com.crisordonez.registro.service

import com.crisordonez.registro.model.errors.BadRequestException
import com.crisordonez.registro.model.errors.ConflictException
import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toEntity
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toResponse
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toUpdateContrasena
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toEntity
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntity
import com.crisordonez.registro.model.requests.AppVersionRequest
import com.crisordonez.registro.model.requests.TiempoChatRequest
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import com.crisordonez.registro.repository.InformacionSocioeconomicaRepository
import com.crisordonez.registro.repository.PacienteRepository
import com.crisordonez.registro.utils.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZoneId
import java.util.*


@Service
class CuentaUsuarioService(
    @Autowired private val cuentaUsuarioRepository: CuentaUsuarioRepository,
    @Autowired private val pacienteRepository: PacienteRepository,
    @Autowired private val informacionSocioeconomicaRepository: InformacionSocioeconomicaRepository,
    @Autowired private val passwordEncoder: PasswordEncoder,
    private val notificacionService: NotificacionServiceInterface
): CuentaUsuarioServiceInterface {

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var cuentaUsuarioDetailService: CuentaUsuarioDetailService

    private val log = LoggerFactory.getLogger(CuentaUsuarioService::class.java)

    override fun crearCuentaUsuario(cuentaUsuario: CuentaUsuarioRequest): CuentaUsuarioResponse {
        log.info("Creando cuenta de usuario - Nombre: ${cuentaUsuario.nombreUsuario}")
        val usuarioDuplicado = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

        if (usuarioDuplicado.isPresent) {
            log.error("La informacion ya esta registrada")
            throw ConflictException("El nombre de usuario ya está registrado")
        }
        if (cuentaUsuario.paciente == null) {
            throw BadRequestException("La informacion del paciente no puede ser nula")
        }
        val cuenta = cuentaUsuarioRepository.save(cuentaUsuario.toEntity(passwordEncoder.encode(cuentaUsuario.contrasena)))
        val paciente = pacienteRepository.save(cuentaUsuario.paciente.toEntity(cuenta))
        cuenta.paciente = paciente
        cuentaUsuarioRepository.save(cuenta)
        if (cuentaUsuario.paciente.infoSocioeconomica != null) {
            val info = informacionSocioeconomicaRepository.save(cuentaUsuario.paciente.infoSocioeconomica.toEntity(paciente))
            paciente.informacionSocioeconomica = info
            pacienteRepository.save(paciente)
        }
        log.info("Cuenta de usuario creada exitosamente, iniciando sesion")
        val token = autenticar(cuentaUsuario)
        return token
    }

    override fun editarCuentaUsuario(publicId: UUID, cuentaUsuario: CuentaUsuarioRequest) {
        log.info("Editando cuenta de usuario - PublicId: $publicId")

        val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

        if (!cuenta.isPresent) {
            throw NotFoundException("La cuenta de usuario no existe")
        }

        val cuentaExistente = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

        if (cuentaExistente.isPresent && cuentaExistente.get().publicId != publicId) {
            throw ConflictException("El nombre de usuario ya está registrado")
        }
        cuentaUsuarioRepository.save(cuentaUsuario.toEntityUpdated(cuenta.get()))
        log.info("Cuenta de usuario actualizada correctamente")
    }

    override fun getAllCuentas(): List<CuentaUsuarioResponse> {
        log.info("Consultando todas las cuentas de usuario")
        val cuentas = cuentaUsuarioRepository.findAll().map { it.toResponse(null) }
        log.info("Cuentas de usuario consultadas - Total: ${cuentas.size}")
        return cuentas
    }

    override fun getCuentaUsuario(publicId: UUID): CuentaUsuarioResponse {
        log.info("Consultando cuenta de usuario - PublicId: $publicId")
        val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

        if (!cuenta.isPresent) {
            throw NotFoundException("La cuenta de usuario no existe")
        }

        log.info("Cuenta de usuario consultada correctamemte")
        return cuenta.get().toResponse(null)
    }

    override fun autenticar(cuentaUsuario: CuentaUsuarioRequest): CuentaUsuarioResponse {
        try {
            log.info("Autenticando usuario - Nombre: ${cuentaUsuario.nombreUsuario}")
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                cuentaUsuario.nombreUsuario, cuentaUsuario.contrasena
            ))
            val token = jwtUtil.generateToken(cuentaUsuarioDetailService.loadUserByUsername(cuentaUsuario.nombreUsuario))
            val usuario = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario).get()
            if (cuentaUsuario.appVersion != null) {
                if (usuario.appVersion == null) {
                    usuario.appVersion = cuentaUsuario.appVersion
                    cuentaUsuarioRepository.save(usuario)
                } else {
                    if (usuario.appVersion != cuentaUsuario.appVersion) {
                        usuario.appVersion = cuentaUsuario.appVersion
                        cuentaUsuarioRepository.save(usuario)
                    }
                }
            }
            return usuario.toResponse(token)
        } catch (ex: BadCredentialsException) {
            throw BadRequestException("Nombre de usuario o contraseña incorrectos")
        } catch (ex: UsernameNotFoundException) {
            throw BadRequestException("El usuario no existe")
        }
    }

    override fun eliminarCuentaUsuario(publicId: UUID) {
        log.info("Eliminando cuenta de usuario - PublicId: $publicId")
        val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

        if (!cuenta.isPresent) {
            throw NotFoundException("La cuenta de usuario no existe")
        }

        cuentaUsuarioRepository.delete(cuenta.get())
        log.info("Cuenta de usuario eliminada correctamente")
    }

    override fun validarExpiracionToken(token: String): String? {
        try {
            log.info("Validando expiracion del token")
            val newToken = jwtUtil.refreshToken(token)
            log.info("Token validado correctamente")
            return newToken
        } catch (e: Exception) {
            throw e
        }
    }

    override fun updateContrasena(cuentaUsuario: CuentaUsuarioRequest) {
        log.info("Cambiando contrasena de la cuenta ${cuentaUsuario.nombreUsuario}")
        val cuenta = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

        if (!cuenta.isPresent) {
            throw NotFoundException("La cuenta de usuario ${cuentaUsuario.nombreUsuario} no existe")
        }

        if (cuentaUsuario.fechaNacimientoCambioPass.isNullOrEmpty()) {
            throw BadRequestException("La fecha de nacimiento es requerida para cambiar la contraseña")
        }

        val fechaNacimiento = cuentaUsuario.fechaNacimientoCambioPass

        log.info(cuenta.get().paciente!!.fechaNacimiento.toString())

        if (SimpleDateFormat("dd/MM/yyyy").format(cuenta.get().paciente!!.fechaNacimiento) != fechaNacimiento) {
            throw BadRequestException("Datos incorrectos, no es posible cambiar la contraseña")
        }

        cuentaUsuarioRepository.save(cuenta.get().toUpdateContrasena(passwordEncoder.encode(cuentaUsuario.contrasena)))
        log.info("Contrasena cambiada correctamente")
    }

    override fun obtenerPublicIdPorIdInterno(id: Long): UUID {
        return cuentaUsuarioRepository.findPublicIdById(id)
            ?: throw NotFoundException("No se encontró el usuario con ese ID interno")
    }

    override fun updateTiempoChat(publicId: UUID, tiempoChatRequest: TiempoChatRequest) {
        log.info("Actualizando tiempo de uso de chat para el usuario - PublicId: $publicId")
        val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

        if (!cuenta.isPresent) {
            throw NotFoundException("La cuenta de usuario no existe")
        }
        val cuentaUsuario = cuenta.get()

        if (tiempoChatRequest.tiempo <= 1) {
            cuentaUsuario.sesionesNoExitosas = cuentaUsuario.sesionesNoExitosas?.plus(1) ?: 1
        } else {
            cuentaUsuario.sesionesExitosas = cuentaUsuario.sesionesExitosas?.plus(1) ?: 1
            cuentaUsuario.tiempoUsoChat = tiempoChatRequest.tiempo
        }
        cuentaUsuarioRepository.save(cuentaUsuario)
        log.info("Tiempo de uso de chat actualizado correctamente para el usuario - PublicId: $publicId")
    }

    override fun getChatTimeAvergae(): Double {
        log.info("Calculando el tiempo promedio de chat de todos los usuarios del sistema")
        val cuentas = cuentaUsuarioRepository.findByTiempoUsoChatIsNotNull()
        if (cuentas.isEmpty()) {
            log.warn("No hay tiempo de chat registrado para los usuarios")
            return 0.0
        }

        val totalChatTime = cuentas.sumOf { it.tiempoUsoChat ?: 0.0 }

        val averageChatTime = totalChatTime / cuentas.size
        log.info("Tiempo promedio de chat calculado: $averageChatTime min(s)")
        return averageChatTime
    }

    override fun stablishAppVersion(publicId: UUID, appVersion: AppVersionRequest) {
        log.info("Estableciendo version de la app para el usuario - PublicId: $publicId, Version: ${appVersion.version}")
        val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

        if (!cuenta.isPresent) {
            throw NotFoundException("La cuenta de usuario no existe")
        }
        val cuentaUsuario = cuenta.get()
        cuentaUsuario.appVersion = appVersion.version
        cuentaUsuarioRepository.save(cuentaUsuario)
        log.info("Version de la app establecida correctamente para el usuario - PublicId: $publicId")
    }

}