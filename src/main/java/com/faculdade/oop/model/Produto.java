package com.faculdade.oop.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome não pode ser vazio")
  private String nome;

  @NotBlank(message = "A categoria não pode ser vazia")
  private String categoria;

  @NotNull(message = "O preço não pode ser vazio")
  private BigDecimal preco;

  @NotNull(message = "A quantidade não pode ser vazia")
  private Integer quantidade;
}
