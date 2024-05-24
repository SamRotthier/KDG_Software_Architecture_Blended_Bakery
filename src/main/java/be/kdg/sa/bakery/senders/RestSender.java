package be.kdg.sa.bakery.senders;

import be.kdg.sa.bakery.config.RabbitTopology;
import be.kdg.sa.bakery.controller.dto.OrderDto;
import be.kdg.sa.bakery.controller.dto.ProductIngredientDto;
import be.kdg.sa.bakery.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

@Service
public class RestSender {

    private static final Logger logger = LoggerFactory.getLogger(RestSender.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    public RestSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendNewProduct(Product product) {
        logger.debug("Trying to send new product message for product: {}", product.getName());
        rabbitTemplate.convertAndSend(RabbitTopology.NEW_PRODUCT_QUEUE, "NEW_PRODUCT_QUEUE",
                (new NewProductMessage(product.getProductId(), product.getName(), product.getIngredients().stream().map(i -> new ProductIngredientDto(i.getIngredient().getId(), i.getIngredient().getName(), i.getQuantity())).toList())));
        logger.info("Delivery message was successfully posted to the NEW_PRODUCT_QUEUE for product: {} ", product.getName());
    }


    public void sendChangeProductState(UUID uuid) {
        logger.debug("Trying to sendDelivery message for UUID: {}", uuid);

        rabbitTemplate.convertAndSend(RabbitTopology.PRODUCT_STATE_QUEUE, "PRODUCT_STATE_QUEUE", uuid);
        logger.info("Delivery message was successfully posted to the PRODUCT_STATE_QUEUE for UUID: {}", uuid);
    }

    public void sendOrderIngredients(OrderDto orderDto) throws JsonProcessingException {
        //logger.debug("Trying to send Order ingredients message for order: {}", orderDto.getid);
        //rabbitTemplate.convertAndSend(RabbitTopology.PRODUCT_STATE_QUEUE, "PRODUCT_STATE_QUEUE",
        //        objectMapper.writeValueAsString(new OrderIngredientMessage (OrderDto.getId, orderDto.getTimestamp, orderDto.getIngredientIt, orderDto.getAmountOrder)));
        //logger.info("Delivery message was successfully posted to the PRODUCT_STATE_QUEUE for order: {}", orderDto.getid);
    }
}
