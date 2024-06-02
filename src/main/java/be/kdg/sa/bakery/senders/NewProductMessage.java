package be.kdg.sa.bakery.senders;

import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.controller.dto.ProductIngredientDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class NewProductMessage {
    public UUID productId;
    public String name;
    public List<ProductIngredientDto> ingredients;

    public NewProductMessage(UUID productId, String name, List<ProductIngredientDto> ingredients) {
        this.productId = productId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public NewProductMessage(UUID productId, String name) {
        this.productId = productId;
        this.name = name;
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

    public List<ProductIngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<ProductIngredientDto> ingredients) {
        this.ingredients = ingredients;
    }
}
