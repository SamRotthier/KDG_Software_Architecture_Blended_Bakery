package be.kdg.sa.bakery.receivers;

import be.kdg.sa.bakery.config.RabbitTopology;
import be.kdg.sa.bakery.controller.dto.OrderDto;
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

    public OrderReceiver(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitTopology.ORDER_PRODUCT_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void receiveNewOrderProduct(OrderDto orderDto) {
        //logger.info("Received a new order message with id: {}", orderDto.getId());
        //orderService.addOrderFromMessage(orderDto);
    }


    @RabbitListener(queues = RabbitTopology.DELIVER_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void receiveNewDeliver(OrderDto orderDto) {
        //logger.info("Received a new order delivery with id: {}", orderDto.getId());
        //orderService.addDeliveryFromMessage(orderDto);
    }

}
