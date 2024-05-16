package be.kdg.sa.bakery.controller.dto;

import java.io.Serializable;
import java.util.UUID;

public class ProductIngredientDto implements Serializable {
    private UUID id;
    private String name;
    private Integer quantity;

    public ProductIngredientDto() {
    }

    public ProductIngredientDto(UUID id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
