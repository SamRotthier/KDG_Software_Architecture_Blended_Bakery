package be.kdg.sa.bakery.repositories;

import be.kdg.sa.bakery.domain.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, UUID> {
    @Query("SELECT pi FROM ProductIngredient pi WHERE pi.product.productId = :productId")
    List<ProductIngredient> findByProductId(@Param("productId") UUID productId);
}
