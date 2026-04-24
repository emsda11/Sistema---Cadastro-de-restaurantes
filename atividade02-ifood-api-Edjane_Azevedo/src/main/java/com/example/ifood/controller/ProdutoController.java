package com.example.ifood.controller;

import com.example.ifood.dto.produto.ProdutoRequestDTO;
import com.example.ifood.dto.produto.ProdutoResponseDTO;
import com.example.ifood.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) { return ResponseEntity.ok(service.criar(dto)); }
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) { return ResponseEntity.ok(service.buscarPorId(id)); }
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto) { return ResponseEntity.ok(service.atualizar(id, dto)); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { service.deletar(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorRestaurante(@PathVariable Long restauranteId) { return ResponseEntity.ok(service.buscarPorRestauranteComJoinFetch(restauranteId)); }
    @GetMapping("/preco-minimo")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorPrecoMinimo(@RequestParam BigDecimal valor) { return ResponseEntity.ok(service.buscarPorPrecoMinimo(valor)); }
}
