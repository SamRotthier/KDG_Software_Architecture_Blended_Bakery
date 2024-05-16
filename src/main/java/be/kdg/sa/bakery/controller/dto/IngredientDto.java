package be.kdg.sa.bakery.controller.dto;

import java.io.Serializable;
import java.util.UUID;

public class IngredientDto implements Serializable {
    private UUID id;
    private String name;

    public IngredientDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
