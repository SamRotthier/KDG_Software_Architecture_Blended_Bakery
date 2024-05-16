package be.kdg.sa.bakery.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ba_product_ingredient")
public class ProductIngredient {
    @Id
    @ManyToOne
    private Product product;
    @Id
    @ManyToOne
    private Ingredient ingredient;
    private int quantity;

    public ProductIngredient() {
    }

    public ProductIngredient(Product product, Ingredient ingredient, int quantity) {
        this.product = product;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
