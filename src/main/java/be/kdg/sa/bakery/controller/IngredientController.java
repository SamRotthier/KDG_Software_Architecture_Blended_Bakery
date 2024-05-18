package be.kdg.sa.bakery.controller;

import be.kdg.sa.bakery.controller.dto.NewIngredientDto;
import be.kdg.sa.bakery.controller.dto.NewProductDto;
import be.kdg.sa.bakery.domain.Ingredient;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.services.IngredientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/new")
    public String newIngredient(Model model){
        model.addAttribute("newIngredientDto", new NewIngredientDto());
        return "ingredients/newIngredient";
    }

    @PostMapping("/new")
    public String addNewIngredient(@Valid @ModelAttribute("newIngredientDto") NewIngredientDto newIngredientDto, RedirectAttributes redirectAttributes){
        ingredientService.createIngredient(newIngredientDto.getName());
        redirectAttributes.addFlashAttribute("successMessage", "Ingredient saved successfully!");
        return "redirect:new";
    }
}