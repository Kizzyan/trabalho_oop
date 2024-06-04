package com.faculdade.oop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.oop.model.Produto;
import com.faculdade.oop.repository.ProdutoRepository;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

  @Autowired
  private ProdutoRepository repository;

  public Produto find(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Produto> findAll() {
    return repository.findAll();
  }

  @Transactional
  public Produto save(Produto produto) {
    Produto produtoSalvo = repository.save(produto);
    return produtoSalvo;
  }

  @Transactional
  public void delete(Produto produto) {
    try {
      repository.delete(produto);
      repository.flush();
    } catch (PersistenceException e) {
      throw new RuntimeException("Não foi possível excluir o registro");
    }
  }

  @Transactional
  public Produto update(Long id, Produto produto) {

    if (!repository.existsById(id)) {
      throw new RuntimeException("Registro com id " + id + " não encontrado");
    }

    return repository.findById(id).map(produtoOriginal -> {
      produtoOriginal.setNome(produto.getNome());
      produtoOriginal.setCategoria(produto.getCategoria());
      produtoOriginal.setPreco(produto.getPreco());
      return repository.save(produtoOriginal);
    }).get();
  }
}
