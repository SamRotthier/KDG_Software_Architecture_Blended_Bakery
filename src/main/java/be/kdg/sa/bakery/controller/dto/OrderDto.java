package be.kdg.sa.bakery.controller.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class OrderDto {
    private UUID id;
    private UUID accountId;

    private Map<UUID, Integer> productQuantities;

    private Instant creationDate;

    public OrderDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Map<UUID, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<UUID, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
