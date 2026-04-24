package com.example.ifood.service;

import com.example.ifood.audit.service.AuditService;
import com.example.ifood.dto.pedido.PedidoRequestDTO;
import com.example.ifood.dto.pedido.PedidoResponseDTO;
import com.example.ifood.exception.BusinessException;
import com.example.ifood.exception.ResourceNotFoundException;
import com.example.ifood.model.Cliente;
import com.example.ifood.model.Pedido;
import com.example.ifood.model.Produto;
import com.example.ifood.repository.main.PedidoRepository;
import com.example.ifood.service.mapper.PedidoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoMapper mapper;
    private final AuditService auditService;

    public PedidoService(PedidoRepository repository,
                         ClienteService clienteService,
                         ProdutoService produtoService,
                         PedidoMapper mapper,
                         AuditService auditService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Transactional("mainTransactionManager")
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Cliente cliente = clienteService.obterEntidade(dto.getClienteId());
        List<Produto> produtos = dto.getProdutosIds().stream().map(produtoService::obterEntidade).toList();
        validarMesmoRestaurante(produtos);
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(dto.getStatus());
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setProdutos(produtos);
        pedido.setValorTotal(calcularValorTotal(produtos));
        Pedido salvo = repository.save(pedido);
        auditService.registrar("Pedido", salvo.getId(), "CREATE");
        return mapper.toDTO(repository.buscarPedidoComProdutos(salvo.getId()));
    }

    public List<PedidoResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = repository.buscarPedidoComProdutos(id);
        if (pedido == null) {
            throw new ResourceNotFoundException("Pedido com id " + id + " não encontrado.");
        }
        return mapper.toDTO(pedido);
    }

    @Transactional("mainTransactionManager")
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido com id " + id + " não encontrado."));
        Cliente cliente = clienteService.obterEntidade(dto.getClienteId());
        List<Produto> produtos = dto.getProdutosIds().stream().map(produtoService::obterEntidade).toList();
        validarMesmoRestaurante(produtos);
        pedido.setCliente(cliente);
        pedido.setStatus(dto.getStatus());
        pedido.setProdutos(produtos);
        pedido.setValorTotal(calcularValorTotal(produtos));
        Pedido salvo = repository.save(pedido);
        auditService.registrar("Pedido", salvo.getId(), "UPDATE");
        return mapper.toDTO(repository.buscarPedidoComProdutos(salvo.getId()));
    }

    @Transactional("mainTransactionManager")
    public void deletar(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido com id " + id + " não encontrado."));
        repository.delete(pedido);
        auditService.registrar("Pedido", id, "DELETE");
    }

    public List<PedidoResponseDTO> buscarPorStatus(String status) {
        return repository.buscarPorStatus(status).stream().map(mapper::toDTO).toList();
    }

    private BigDecimal calcularValorTotal(List<Produto> produtos) {
        return produtos.stream().map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validarMesmoRestaurante(List<Produto> produtos) {
        long q = produtos.stream().map(p -> p.getRestaurante().getId()).distinct().count();
        if (q > 1) {
            throw new BusinessException("Um pedido deve conter produtos do mesmo restaurante.");
        }
    }
}
