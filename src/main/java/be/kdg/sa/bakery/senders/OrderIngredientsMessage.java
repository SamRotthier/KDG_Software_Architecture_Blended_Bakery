package be.kdg.sa.bakery.senders;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class OrderIngredientsMessage {
    public UUID id;
    public Instant bakeStartTimestamp;
    public Map<UUID, Integer> ingredients;

    public OrderIngredientsMessage(UUID id, Instant bakeStartTimestamp, Map<UUID, Integer> ingredients) {
        this.id = id;
        this.bakeStartTimestamp = bakeStartTimestamp;
        this.ingredients = ingredients;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Instant getBakeStartTimestamp() {
        return bakeStartTimestamp;
    }

    public void setBakeStartTimestamp(Instant bakeStartTimestamp) {
        this.bakeStartTimestamp = bakeStartTimestamp;
    }

    public Map<UUID, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<UUID, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<UUID, Integer> getProductQuantities() {
        return ingredients;
    }

    public void setProductQuantities(Map<UUID, Integer> ingredients) {
        this.ingredients = ingredients;
    }
}
