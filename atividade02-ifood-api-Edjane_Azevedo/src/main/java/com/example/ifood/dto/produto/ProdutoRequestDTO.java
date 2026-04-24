package com.example.ifood.dto.produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoRequestDTO {
    @NotBlank(message = "O nome do produto é obrigatório.")
    private String nome;
    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    private BigDecimal preco;
    @NotNull(message = "O id do restaurante é obrigatório.")
    private Long restauranteId;
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public Long getRestauranteId() { return restauranteId; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }
}
