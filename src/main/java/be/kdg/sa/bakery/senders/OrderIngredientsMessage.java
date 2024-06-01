package be.kdg.sa.bakery.senders;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class OrderIngredientsMessage {
    public UUID id;
    public Instant creationDate;
    public Map<UUID, Integer> productQuantities;

    public OrderIngredientsMessage(UUID id, Instant creationDate, Map<UUID, Integer> productQuantities) {
        this.id = id;
        this.creationDate = creationDate;
        this.productQuantities = productQuantities;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Map<UUID, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<UUID, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}
