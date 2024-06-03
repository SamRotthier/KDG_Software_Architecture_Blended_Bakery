package be.kdg.sa.bakery.controller.dto;

import java.io.Serializable;
import java.util.UUID;

public class ProductIngredientDto implements Serializable {
    private UUID ingredientId;
    private String name;
    private Integer quantity;

    public ProductIngredientDto() {
    }

    public ProductIngredientDto(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ProductIngredientDto(UUID ingredientId, String name, Integer quantity) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.quantity = quantity;
    }

    public UUID getId() {
        return ingredientId;
    }

    public void setId(UUID id) {
        this.ingredientId = id;
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
