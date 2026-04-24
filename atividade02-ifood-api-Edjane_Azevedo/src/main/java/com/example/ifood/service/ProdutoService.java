package com.example.ifood.service;

import com.example.ifood.audit.service.AuditService;
import com.example.ifood.dto.produto.ProdutoRequestDTO;
import com.example.ifood.dto.produto.ProdutoResponseDTO;
import com.example.ifood.exception.ResourceNotFoundException;
import com.example.ifood.model.Produto;
import com.example.ifood.model.Restaurante;
import com.example.ifood.repository.main.ProdutoRepository;
import com.example.ifood.service.mapper.ProdutoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final RestauranteService restauranteService;
    private final ProdutoMapper mapper;
    private final AuditService auditService;

    public ProdutoService(ProdutoRepository repository,
                          RestauranteService restauranteService,
                          ProdutoMapper mapper,
                          AuditService auditService) {
        this.repository = repository;
        this.restauranteService = restauranteService;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Transactional("mainTransactionManager")
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Restaurante restaurante = restauranteService.obterEntidade(dto.getRestauranteId());
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setRestaurante(restaurante);
        Produto salvo = repository.save(produto);
        auditService.registrar("Produto", salvo.getId(), "CREATE");
        return mapper.toDTO(salvo);
    }

    public List<ProdutoResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return mapper.toDTO(obterEntidade(id));
    }

    @Transactional("mainTransactionManager")
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = obterEntidade(id);
        Restaurante restaurante = restauranteService.obterEntidade(dto.getRestauranteId());
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setRestaurante(restaurante);
        Produto atualizado = repository.save(produto);
        auditService.registrar("Produto", atualizado.getId(), "UPDATE");
        return mapper.toDTO(atualizado);
    }

    @Transactional("mainTransactionManager")
    public void deletar(Long id) {
        Produto produto = obterEntidade(id);
        repository.delete(produto);
        auditService.registrar("Produto", id, "DELETE");
    }

    public List<ProdutoResponseDTO> buscarPorRestauranteComJoinFetch(Long restauranteId) {
        return repository.buscarProdutosComRestaurante(restauranteId).stream().map(mapper::toDTO).toList();
    }

    public List<ProdutoResponseDTO> buscarPorPrecoMinimo(BigDecimal precoMinimo) {
        return repository.buscarPorPrecoMinimo(precoMinimo).stream().map(mapper::toDTO).toList();
    }

    public Produto obterEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + id + " não encontrado."));
    }
}
