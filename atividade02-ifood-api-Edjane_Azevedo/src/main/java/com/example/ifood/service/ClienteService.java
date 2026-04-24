package com.example.ifood.service;

import com.example.ifood.audit.service.AuditService;
import com.example.ifood.dto.cliente.ClienteRequestDTO;
import com.example.ifood.dto.cliente.ClienteResponseDTO;
import com.example.ifood.exception.BusinessException;
import com.example.ifood.exception.ResourceNotFoundException;
import com.example.ifood.model.Cliente;
import com.example.ifood.repository.main.ClienteRepository;
import com.example.ifood.service.mapper.ClienteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final AuditService auditService;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper, AuditService auditService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Transactional("mainTransactionManager")
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());

        Cliente salvo = repository.save(cliente);
        auditService.registrar("Cliente", salvo.getId(), "CREATE");

        return mapper.toDTO(salvo);
    }

    public List<ClienteResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return mapper.toDTO(obterEntidade(id));
    }

    @Transactional("mainTransactionManager")
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = obterEntidade(id);
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        Cliente atualizado = repository.save(cliente);
        auditService.registrar("Cliente", atualizado.getId(), "UPDATE");
        return mapper.toDTO(atualizado);
    }

    @Transactional("mainTransactionManager")
    public void deletar(Long id) {
        Cliente cliente = obterEntidade(id);
        if (!cliente.getPedidos().isEmpty()) {
            throw new BusinessException("Não é possível remover cliente com pedidos vinculados.");
        }
        repository.delete(cliente);
        auditService.registrar("Cliente", id, "DELETE");
    }

    public List<ClienteResponseDTO> buscarPorNome(String nome) {
        return repository.buscarPorNomeParcial(nome).stream().map(mapper::toDTO).toList();
    }

    public Cliente obterEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " não encontrado."));
    }
}
