package com.example.ifood.dto.pedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
public class PedidoRequestDTO {
    @NotNull(message = "O id do cliente é obrigatório.")
    private Long clienteId;
    @NotBlank(message = "O status do pedido é obrigatório.")
    private String status;
    @NotEmpty(message = "A lista de produtos não pode estar vazia.")
    private List<Long> produtosIds;
    public Long getClienteId() { return clienteId; }
    public String getStatus() { return status; }
    public List<Long> getProdutosIds() { return produtosIds; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public void setStatus(String status) { this.status = status; }
    public void setProdutosIds(List<Long> produtosIds) { this.produtosIds = produtosIds; }
}
