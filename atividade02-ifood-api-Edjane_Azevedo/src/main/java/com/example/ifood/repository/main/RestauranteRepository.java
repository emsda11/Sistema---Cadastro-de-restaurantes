package com.example.ifood.repository.main;
import com.example.ifood.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    @Query(value = "SELECT * FROM restaurantes r WHERE LOWER(r.categoria) = LOWER(:categoria)", nativeQuery = true)
    List<Restaurante> buscarPorCategoriaNativa(String categoria);
}
