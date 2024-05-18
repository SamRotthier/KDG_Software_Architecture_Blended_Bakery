package be.kdg.sa.bakery.controller;

import be.kdg.sa.bakery.controller.dto.IngredientDto;
import be.kdg.sa.bakery.services.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientsRestController {
    private final IngredientService ingredientService;

    public IngredientsRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("")
    public List<IngredientDto> getIngredients(){

        return ingredientService.getIngredients().stream().map(i -> new IngredientDto(i.getId(), i.getName())).toList();
    }
}
