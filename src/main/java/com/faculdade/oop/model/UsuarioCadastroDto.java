package com.faculdade.oop.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {
  @Email
  @NotBlank
  private String email;
  @NotBlank
  private String senha;
  @NotBlank
  private String nome;
}
