package com.example.ifood.controller;

import com.example.ifood.dto.restaurante.RestauranteRequestDTO;
import com.example.ifood.dto.restaurante.RestauranteResponseDTO;
import com.example.ifood.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {
    private final RestauranteService service;
    public RestauranteController(RestauranteService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criar(@Valid @RequestBody RestauranteRequestDTO dto) { return ResponseEntity.ok(service.criar(dto)); }
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) { return ResponseEntity.ok(service.buscarPorId(id)); }
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteRequestDTO dto) { return ResponseEntity.ok(service.atualizar(id, dto)); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { service.deletar(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/categoria")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@RequestParam String nome) { return ResponseEntity.ok(service.buscarPorCategoria(nome)); }
}
