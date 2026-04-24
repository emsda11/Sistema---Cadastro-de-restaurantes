package com.example.ifood.service.mapper;
import com.example.ifood.dto.cliente.ClienteResponseDTO;
import com.example.ifood.model.Cliente;
import org.springframework.stereotype.Component;
@Component
public class ClienteMapper {
    public ClienteResponseDTO toDTO(Cliente cliente) {
        int quantidadePedidos = cliente.getPedidos() == null ? 0 : cliente.getPedidos().size();
        return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(), quantidadePedidos);
    }
}
