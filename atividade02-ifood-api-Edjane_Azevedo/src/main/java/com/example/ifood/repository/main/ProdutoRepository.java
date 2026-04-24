package com.example.ifood.repository.main;
import com.example.ifood.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p JOIN FETCH p.restaurante r WHERE r.id = :restauranteId")
    List<Produto> buscarProdutosComRestaurante(Long restauranteId);

    @Query(value = "SELECT * FROM produtos p WHERE p.preco >= :precoMinimo", nativeQuery = true)
    List<Produto> buscarPorPrecoMinimo(BigDecimal precoMinimo);
}
