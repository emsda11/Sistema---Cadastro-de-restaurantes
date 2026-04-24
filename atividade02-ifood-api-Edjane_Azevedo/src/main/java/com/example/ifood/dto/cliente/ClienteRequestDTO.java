package com.example.ifood.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteRequestDTO {
    @NotBlank(message = "O nome do cliente é obrigatório.")
    private String nome;
    @NotBlank(message = "O email do cliente é obrigatório.")
    @Email(message = "O email do cliente deve ser válido.")
    private String email;
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
}
