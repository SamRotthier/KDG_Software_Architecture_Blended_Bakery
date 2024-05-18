package be.kdg.sa.bakery.domain;

import be.kdg.sa.bakery.domain.Enum.ProductState;
import be.kdg.sa.bakery.domain.Enum.RecipeState;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ba_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "product")
    private List<ProductIngredient> ingredients;

    @OneToMany(mappedBy = "product")
    private List<RecipeStep> recipeSteps;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Enumerated(EnumType.STRING)
    private RecipeState recipeState;

    public Product() {
    }

    public Product(String name, String description, ProductState productState, RecipeState recipeState) {
        this.name = name;
        this.description = description;
        this.productState = productState;
        this.recipeState = recipeState;
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
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Enter a name.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null || description.equals("")){
            throw new IllegalArgumentException("Enter a description.");
        }
        this.description = description;
    }

    public ProductState getProductState() {
        return productState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public List<ProductIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<ProductIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public RecipeState getRecipeState() {
        return recipeState;
    }

    public void setRecipeState(RecipeState recipeState) {
        this.recipeState = recipeState;
    }
}
