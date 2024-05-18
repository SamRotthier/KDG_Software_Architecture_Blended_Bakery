package be.kdg.sa.bakery.repositories;

import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}
