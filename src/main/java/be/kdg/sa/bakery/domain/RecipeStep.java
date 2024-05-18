package be.kdg.sa.bakery.domain;

import be.kdg.sa.bakery.controller.dto.ProductDto;
import jakarta.persistence.*;

@Entity
@Table(name = "ba_recipe_step")
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int step;
    private String description;
    @ManyToOne(optional = false)
    private Product product;
    public RecipeStep() {
    }

    public RecipeStep(long id, int step, String description, Product product) {
        this.id = id;
        this.step = step;
        this.description = description;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
