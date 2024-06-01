package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.dto.IngredientDto;
import be.kdg.sa.bakery.domain.Ingredient;
import be.kdg.sa.bakery.repositories.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public void createIngredient(String name){
        logger.info("Creating ingredient with name: {}", name);
        Ingredient ingredient = ingredientRepository.save(new Ingredient(name));
    }

    public List<Ingredient> getIngredients(){
        logger.info("Fetching all ingredients");
        return ingredientRepository.findAll();
    }
}
