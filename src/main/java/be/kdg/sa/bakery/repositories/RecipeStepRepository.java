package be.kdg.sa.bakery.repositories;

import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    @Query("SELECT rs FROM RecipeStep rs WHERE rs.product.productId = :productId")
    List<RecipeStep> findByProductId(@Param("productId") UUID productId);
}
