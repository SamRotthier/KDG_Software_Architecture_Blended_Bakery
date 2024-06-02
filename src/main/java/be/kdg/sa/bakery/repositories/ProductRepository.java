package be.kdg.sa.bakery.repositories;

import be.kdg.sa.bakery.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.productId IN :ids")
    List<Product> findByProductIdIn(@Param("ids") List<UUID> ids);

}
