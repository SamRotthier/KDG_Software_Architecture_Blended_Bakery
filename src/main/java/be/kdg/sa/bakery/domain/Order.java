package be.kdg.sa.bakery.domain;

import be.kdg.sa.bakery.domain.Enum.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ba_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> products;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private UUID accountId;
    private Instant creationTimestamp;
    private Instant bakedTimestamp;

    public Order() {
    }

    public Order(UUID id, UUID accountId, Instant creationTimestamp) {
        this.id = id;
        this.accountId = accountId;
        this.creationTimestamp = creationTimestamp;
        this.status = OrderStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Instant getBakedTimestamp() {
        return bakedTimestamp;
    }

    public void setBakedTimestamp(Instant bakedTimestamp) {
        this.bakedTimestamp = bakedTimestamp;
    }
}
