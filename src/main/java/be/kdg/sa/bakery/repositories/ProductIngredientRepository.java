package be.kdg.sa.bakery.repositories;

import be.kdg.sa.bakery.domain.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, UUID> {
}
