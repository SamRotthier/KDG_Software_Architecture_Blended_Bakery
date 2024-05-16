package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.domain.Ingredient;
import be.kdg.sa.bakery.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public void createIngredient(String name){
        Ingredient ingredient = ingredientRepository.save(new Ingredient(name));
    }
}
