package com.faculdade.oop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

  @Autowired
  private CadastroUsuarioService service;
  @Autowired
  private UserAuthProvider provider;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody CredenciaisModel credenciaisModel) {
    try {
      UsuarioModel usuario = service.login(credenciaisModel);
      usuario.setToken(provider.createToken(usuario));
      return ResponseEntity.ok(usuario);
    } catch (SenhaInvalidaException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @PostMapping("/cadastro")
  public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioInput usuarioInput) {
    try {
      UsuarioModel usuario = service.salvar(usuarioInput);
      usuario.setToken(provider.createToken(usuario));
      return ResponseEntity.ok(usuario);
    } catch (EntidadeJaCadastradaException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }
}
