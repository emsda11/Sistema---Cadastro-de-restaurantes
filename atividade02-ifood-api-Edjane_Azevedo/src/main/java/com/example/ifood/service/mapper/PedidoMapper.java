package com.example.ifood.service.mapper;
import com.example.ifood.dto.pedido.PedidoResponseDTO;
import com.example.ifood.model.Pedido;
import org.springframework.stereotype.Component;
@Component
public class PedidoMapper {
    public PedidoResponseDTO toDTO(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getStatus(),
                pedido.getDataCriacao(),
                pedido.getValorTotal(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getProdutos().stream().map(p -> p.getNome()).toList()
        );
    }
}
