package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.model.errors.NotFoundException
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toUpdateUltimaSesion
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CuentaUsuarioDetailService : UserDetailsService {

    @Autowired
    lateinit var cuentaUsuarioRepository: CuentaUsuarioRepository

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        log.info("Autenticando - Nombre usuario: $username")
        val usuario = cuentaUsuarioRepository.findByNombreUsuario(username!!)
        if (!usuario.isPresent) {
            throw NotFoundException("El nombre de usuario no existe $username")
        }
        cuentaUsuarioRepository.save(usuario.get().toUpdateUltimaSesion())
        return User.builder()
            .username(usuario.get().nombreUsuario)
            .password(usuario.get().contrasena)
            .roles(usuario.get().rol)
            .build()

    }

    fun getRoles(usuario: CuentaUsuarioEntity): List<String> {
        return usuario.rol.split(",")
    }
}