package com.example.ifood.service.mapper;
import com.example.ifood.dto.produto.ProdutoResponseDTO;
import com.example.ifood.model.Produto;
import org.springframework.stereotype.Component;
@Component
public class ProdutoMapper {
    public ProdutoResponseDTO toDTO(Produto produto) {
        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.getRestaurante().getId(), produto.getRestaurante().getNome());
    }
}
