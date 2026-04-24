package com.example.ifood.service.mapper;
import com.example.ifood.dto.restaurante.RestauranteResponseDTO;
import com.example.ifood.model.Restaurante;
import org.springframework.stereotype.Component;
@Component
public class RestauranteMapper {
    public RestauranteResponseDTO toDTO(Restaurante restaurante) {
        int quantidadeProdutos = restaurante.getProdutos() == null ? 0 : restaurante.getProdutos().size();
        return new RestauranteResponseDTO(restaurante.getId(), restaurante.getNome(), restaurante.getCategoria(), quantidadeProdutos);
    }
}
