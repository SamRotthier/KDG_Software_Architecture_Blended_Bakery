package be.kdg.sa.bakery.controller.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewProductDto {
    private String name;
    private String description;
    private Map<UUID, Integer> ingredients;

    public NewProductDto(String name, String description, Map<UUID, Integer> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public NewProductDto() {
        this(null, null, new HashMap<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<UUID, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<UUID, Integer> ingredients) {
        this.ingredients = ingredients;
    }
}
