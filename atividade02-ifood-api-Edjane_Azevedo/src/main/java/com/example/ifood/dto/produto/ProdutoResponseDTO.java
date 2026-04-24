package com.example.ifood.dto.produto;
import java.math.BigDecimal;
public class ProdutoResponseDTO {
    private Long id; private String nome; private BigDecimal preco; private Long restauranteId; private String restauranteNome;
    public ProdutoResponseDTO() {}
    public ProdutoResponseDTO(Long id, String nome, BigDecimal preco, Long restauranteId, String restauranteNome) {
        this.id=id; this.nome=nome; this.preco=preco; this.restauranteId=restauranteId; this.restauranteNome=restauranteNome;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public Long getRestauranteId() { return restauranteId; }
    public String getRestauranteNome() { return restauranteNome; }
    public void setId(Long id) { this.id=id; }
    public void setNome(String nome) { this.nome=nome; }
    public void setPreco(BigDecimal preco) { this.preco=preco; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId=restauranteId; }
    public void setRestauranteNome(String restauranteNome) { this.restauranteNome=restauranteNome; }
}
