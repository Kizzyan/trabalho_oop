package com.faculdade.oop.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faculdade.oop.model.Usuario;
import com.faculdade.oop.model.UsuarioCadastroDto;
import com.faculdade.oop.model.UsuarioLoginDto;
import com.faculdade.oop.repository.UsuarioRepository;

@Service
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public UsuarioService(
      UsuarioRepository usuarioRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Usuario signup(UsuarioCadastroDto input) {
    Usuario usuario = new Usuario();
    usuario.setNome(input.getNome());
    usuario.setEmail(input.getEmail());
    usuario.setSenha(passwordEncoder.encode(input.getSenha()));
    return usuarioRepository.save(usuario);
  }

  public Usuario authenticate(UsuarioLoginDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getEmail(),
            input.getSenha()));

    return usuarioRepository.findByEmail(input.getEmail())
        .orElseThrow();
  }
}
