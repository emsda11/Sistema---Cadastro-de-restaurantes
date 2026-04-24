package com.example.ifood.dto.cliente;

public class ClienteResponseDTO {
    private Long id; private String nome; private String email; private Integer quantidadePedidos;
    public ClienteResponseDTO() {}
    public ClienteResponseDTO(Long id, String nome, String email, Integer quantidadePedidos) {
        this.id=id; this.nome=nome; this.email=email; this.quantidadePedidos=quantidadePedidos;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Integer getQuantidadePedidos() { return quantidadePedidos; }
    public void setId(Long id) { this.id=id; }
    public void setNome(String nome) { this.nome=nome; }
    public void setEmail(String email) { this.email=email; }
    public void setQuantidadePedidos(Integer quantidadePedidos) { this.quantidadePedidos=quantidadePedidos; }
}
