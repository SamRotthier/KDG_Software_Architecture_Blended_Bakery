package be.kdg.sa.bakery.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ba_ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "ingredient")
    private List<ProductIngredient> products;

    public Ingredient() {
    }

    public Ingredient(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(String name) {
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

    public List<ProductIngredient> getProducts() {
        return products;
    }

    public void setProducts(List<ProductIngredient> products) {
        this.products = products;
    }
}
