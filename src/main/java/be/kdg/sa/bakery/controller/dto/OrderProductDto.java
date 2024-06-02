package be.kdg.sa.bakery.controller.dto;

import java.util.UUID;

public class OrderProductDto {
    private UUID id;
    private int quantity;
    private UUID orderId;
    private UUID productId;

    public OrderProductDto(UUID id, int quantity, UUID orderId, UUID productId) {
        this.id = id;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
    }

    public OrderProductDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
