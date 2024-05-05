package be.kdg.sa.bakery.controller.dto;

import be.kdg.sa.bakery.domain.ProductState;

import java.io.Serializable;
import java.util.UUID;

public class ProductDto implements Serializable {
    private UUID productId;
    private String name;
    private String description;
    private ProductState productState;

    public ProductDto(UUID productId, String name, String description, ProductState productState) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.productState = productState;
    }

    public ProductDto() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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

    public ProductState getProductState() {
        return productState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }
}
