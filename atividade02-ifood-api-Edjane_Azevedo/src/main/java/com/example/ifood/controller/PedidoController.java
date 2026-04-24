package com.example.ifood.controller;

import com.example.ifood.dto.pedido.PedidoRequestDTO;
import com.example.ifood.dto.pedido.PedidoResponseDTO;
import com.example.ifood.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dto) { return ResponseEntity.ok(service.criar(dto)); }
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() { return ResponseEntity.ok(service.listar()); }
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) { return ResponseEntity.ok(service.buscarPorId(id)); }
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO dto) { return ResponseEntity.ok(service.atualizar(id, dto)); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { service.deletar(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/status")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorStatus(@RequestParam String valor) { return ResponseEntity.ok(service.buscarPorStatus(valor)); }
}
