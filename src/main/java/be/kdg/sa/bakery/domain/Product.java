package be.kdg.sa.bakery.domain;

import be.kdg.sa.bakery.domain.Enum.ProductState;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ba_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    public Product() {
    }

    public Product(UUID productId, String name, String description) {
        this.productId = productId;
        this.name = name;
        this.description = description;
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
}
