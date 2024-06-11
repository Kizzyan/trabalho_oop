package com.faculdade.oop.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.faculdade.oop.model.Venda;
import com.faculdade.oop.repository.VendaRepository;

import jakarta.validation.Valid;

@RequestMapping("/vendas")
@RestController
public class VendaController {

  private final VendaRepository repository;
  private static Venda relatorio;

  public VendaController(VendaRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/salvar")
  public ResponseEntity<String> salvar(@RequestBody @Valid Venda venda) {
    relatorio.setDetalhamento(relatorio.getDetalhamento() + "\n" + venda.getDetalhamento());
    relatorio.setValor(relatorio.getValor().add(venda.getValor()));
    return ResponseEntity.status(201).body("Venda registrada com sucesso!");
  }

  @PostMapping("/gerar-relatorio")
  public ResponseEntity<Venda> geraRelatorio() {
    relatorio.setData(LocalDate.now());
    return ResponseEntity.ok(repository.save(relatorio));
  }

  @PostMapping("/relatorios")
  public ResponseEntity<List<Venda>> visualizarRelatorios() {
    return ResponseEntity.ok(repository.findAll());
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void triggerMidnightRoute() {
    Optional<Venda> venda = repository.findByData(LocalDate.now().minusDays(1));
    if (!venda.isPresent()) {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getForObject("http://localhost:8080/gerar-relatorio", String.class);
    }
  }
}
