package com.faculdade.oop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.oop.model.Produto;
import com.faculdade.oop.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

  private final ProdutoService service;

  public ProdutoController(ProdutoService service) {
    this.service = service;
  }

  @GetMapping("/listar")
  public ResponseEntity<List<Produto>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produto> find(@PathVariable("id") Long id) {
    return ResponseEntity.ok(service.find(id));
  }

  @PostMapping("/salvar")
  public ResponseEntity<Produto> save(@RequestBody @Valid Produto produto) {
    return ResponseEntity.status(201).body(service.save(produto));
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody @Valid Produto produto) {
    return ResponseEntity.ok(service.update(id, produto));
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.delete(service.find(id));
    return ResponseEntity.noContent().build();
  }
}
