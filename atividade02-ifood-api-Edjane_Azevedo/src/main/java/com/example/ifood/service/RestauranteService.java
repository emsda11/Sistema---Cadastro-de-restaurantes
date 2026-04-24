package com.example.ifood.service;

import com.example.ifood.audit.service.AuditService;
import com.example.ifood.dto.restaurante.RestauranteRequestDTO;
import com.example.ifood.dto.restaurante.RestauranteResponseDTO;
import com.example.ifood.exception.BusinessException;
import com.example.ifood.exception.ResourceNotFoundException;
import com.example.ifood.model.Restaurante;
import com.example.ifood.repository.main.RestauranteRepository;
import com.example.ifood.service.mapper.RestauranteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository repository;
    private final RestauranteMapper mapper;
    private final AuditService auditService;

    public RestauranteService(RestauranteRepository repository, RestauranteMapper mapper, AuditService auditService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Transactional("mainTransactionManager")
    public RestauranteResponseDTO criar(RestauranteRequestDTO dto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        Restaurante salvo = repository.save(restaurante);
        auditService.registrar("Restaurante", salvo.getId(), "CREATE");
        return mapper.toDTO(salvo);
    }

    public List<RestauranteResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public RestauranteResponseDTO buscarPorId(Long id) {
        return mapper.toDTO(obterEntidade(id));
    }

    @Transactional("mainTransactionManager")
    public RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto) {
        Restaurante restaurante = obterEntidade(id);
        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        Restaurante atualizado = repository.save(restaurante);
        auditService.registrar("Restaurante", atualizado.getId(), "UPDATE");
        return mapper.toDTO(atualizado);
    }

    @Transactional("mainTransactionManager")
    public void deletar(Long id) {
        Restaurante restaurante = obterEntidade(id);
        if (!restaurante.getProdutos().isEmpty()) {
            throw new BusinessException("Não é possível remover restaurante com produtos vinculados.");
        }
        repository.delete(restaurante);
        auditService.registrar("Restaurante", id, "DELETE");
    }

    public List<RestauranteResponseDTO> buscarPorCategoria(String categoria) {
        return repository.buscarPorCategoriaNativa(categoria).stream().map(mapper::toDTO).toList();
    }

    public Restaurante obterEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante com id " + id + " não encontrado."));
    }
}
