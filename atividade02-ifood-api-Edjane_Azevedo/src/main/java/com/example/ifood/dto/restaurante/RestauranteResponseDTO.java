package com.example.ifood.dto.restaurante;
public class RestauranteResponseDTO {
    private Long id; private String nome; private String categoria; private Integer quantidadeProdutos;
    public RestauranteResponseDTO() {}
    public RestauranteResponseDTO(Long id, String nome, String categoria, Integer quantidadeProdutos) {
        this.id=id; this.nome=nome; this.categoria=categoria; this.quantidadeProdutos=quantidadeProdutos;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public Integer getQuantidadeProdutos() { return quantidadeProdutos; }
    public void setId(Long id) { this.id=id; }
    public void setNome(String nome) { this.nome=nome; }
    public void setCategoria(String categoria) { this.categoria=categoria; }
    public void setQuantidadeProdutos(Integer quantidadeProdutos) { this.quantidadeProdutos=quantidadeProdutos; }
}
