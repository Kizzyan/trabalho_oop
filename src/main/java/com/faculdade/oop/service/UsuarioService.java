package com.faculdade.oop.service;

import java.nio.CharBuffer;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UsuarioRepository repositorio;
  @Autowired
  private UsuarioAssembler assembler;

  public UsuarioModel login(CredenciaisModel credenciais) {
    Usuario usuario = repositorio.findByLogin(credenciais.login())
        .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
    if (passwordEncoder.matches(CharBuffer.wrap(credenciais.senha()), usuario.getSenha())) {
      return assembler.toModel(usuario);

    }
    throw new SenhaInvalidaException("Senha inválida");
  }

public UsuarioModel salvar(UsuarioInput usuarioInput) {
Optional<Usuario> usuarioExistente = repositorio.findByLogin(usuarioInput.getLogin());
if (usuarioExistente.isPresent() && (usuarioExistente.get().getId() != usuarioInput.getId())) {
throw new EntidadeJaCadastradaException("Já há um usuario com mesmo login
cadastrado");
}
Usuario usuarioResposta = assembler.toEntity(usuarioInput);
usuarioResposta.setSenha(passwordEncoder.encode(usuarioInput.getSenha()));
Usuario usuario = repositorio.save(usuarioResposta);
return assembler.toModel(usuario);
}
}
