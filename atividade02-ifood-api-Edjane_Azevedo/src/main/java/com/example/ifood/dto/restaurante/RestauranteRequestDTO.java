package com.example.ifood.dto.restaurante;
import jakarta.validation.constraints.NotBlank;
public class RestauranteRequestDTO {
    @NotBlank(message = "O nome do restaurante é obrigatório.")
    private String nome;
    @NotBlank(message = "A categoria do restaurante é obrigatória.")
    private String categoria;
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
