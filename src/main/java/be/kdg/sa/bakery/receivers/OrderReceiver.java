package be.kdg.sa.bakery.receivers;

import be.kdg.sa.bakery.config.RabbitTopology;
import be.kdg.sa.bakery.controller.dto.OrderDto;
import be.kdg.sa.bakery.controller.dto.OrderMessageDto;
import be.kdg.sa.bakery.services.BakingService;
import be.kdg.sa.bakery.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderReceiver {

    private static final Logger logger = LoggerFactory.getLogger(OrderReceiver.class);
    private final OrderService orderService;
    private final BakingService bakingService;

    public OrderReceiver(OrderService orderService, BakingService bakingService) {
        this.orderService = orderService;
        this.bakingService = bakingService;
    }

    @RabbitListener(queues = RabbitTopology.ORDER_PRODUCT_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void receiveNewOrderProduct(OrderDto orderDto) {
        logger.info("Received a new order message with id: {}", orderDto.getOrderId());
        orderService.addOrder(orderDto);
    }


    @RabbitListener(queues = RabbitTopology.DELIVER_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void receiveNewDelivery(OrderMessageDto orderMessageDto) {
        logger.info("Received a new order delivery with id: {}", orderMessageDto.getId());
        orderService.receiveDeliveredIngredients(orderMessageDto);
    }
}
