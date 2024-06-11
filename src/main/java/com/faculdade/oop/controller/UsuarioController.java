package com.faculdade.oop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.oop.model.LoginResposta;
import com.faculdade.oop.model.Usuario;
import com.faculdade.oop.model.UsuarioCadastroDto;
import com.faculdade.oop.model.UsuarioLoginDto;
import com.faculdade.oop.service.JwtService;
import com.faculdade.oop.service.UsuarioService;

@RequestMapping("/auth")
@RestController
public class UsuarioController {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public UsuarioController(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioCadastroDto usuarioCadastroDto) {
        Usuario registeredUsuario = usuarioService.signup(usuarioCadastroDto);

        return ResponseEntity.ok(registeredUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResposta> authenticate(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        Usuario authenticatedUsuario = usuarioService.authenticate(usuarioLoginDto);

        String jwtToken = jwtService.generateToken(authenticatedUsuario);

        LoginResposta loginResposta = new LoginResposta();
        loginResposta.setToken(jwtToken);
        loginResposta.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResposta);
    }
}
