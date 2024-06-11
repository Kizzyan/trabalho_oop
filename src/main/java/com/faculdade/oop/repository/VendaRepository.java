package com.faculdade.oop.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faculdade.oop.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
  Optional<Venda> findByData(LocalDate data);
}
