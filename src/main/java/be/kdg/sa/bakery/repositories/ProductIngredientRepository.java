package be.kdg.sa.bakery.repositories;

import be.kdg.sa.bakery.domain.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, UUID> {
}
