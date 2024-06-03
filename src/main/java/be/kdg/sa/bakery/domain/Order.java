package be.kdg.sa.bakery.domain;

import be.kdg.sa.bakery.domain.Enum.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ba_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderProduct> products;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private UUID accountId;
    @CreationTimestamp
    private Instant creationTimestamp;
    private Instant bakeStartTimestamp;

    private Instant ingredientsReceivedTimestamp;

    private Instant bakeFinishTimestamp;

    public Order() {
    }

    public Order(UUID id, UUID accountId) {
        this.id = id;
        this.accountId = accountId;
        this.orderStatus = OrderStatus.OPEN;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
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

    public Instant getBakeStartTimestamp() {
        return bakeStartTimestamp;
    }

    public void setBakeStartTimestamp(Instant bakeStartTimestamp) {
        this.bakeStartTimestamp = bakeStartTimestamp;
    }

    public Instant getBakeFinishTimestamp() {
        return bakeFinishTimestamp;
    }

    public void setBakeFinishTimestamp(Instant bakeFinishTimestamp) {
        this.bakeFinishTimestamp = bakeFinishTimestamp;
    }

    public Instant getIngredientsReceivedTimestamp() {
        return ingredientsReceivedTimestamp;
    }

    public void setIngredientsReceivedTimestamp(Instant ingredientsReceivedTimestamp) {
        this.ingredientsReceivedTimestamp = ingredientsReceivedTimestamp;
    }
}
