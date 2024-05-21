package be.kdg.sa.bakery.senders;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class OrderIngredientsMessage {
    private UUID id;
    private Instant creationDate;
    private Map<UUID, Integer> productQuantities;

}
