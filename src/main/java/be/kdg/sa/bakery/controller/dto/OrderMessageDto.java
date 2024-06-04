package be.kdg.sa.bakery.controller.dto;

import be.kdg.sa.bakery.domain.Enum.OrderStatus;
import be.kdg.sa.bakery.domain.Enum.ReceivedOrderStatusWarehouse;

import java.time.Instant;
import java.util.UUID;

public class OrderMessageDto {
    private UUID id;
    private Instant currentTime;
    private ReceivedOrderStatusWarehouse orderStatus;

    public OrderMessageDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Instant currentTime) {
        this.currentTime = currentTime;
    }

    public ReceivedOrderStatusWarehouse getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ReceivedOrderStatusWarehouse orderStatus) {
        this.orderStatus = orderStatus;
    }
}
