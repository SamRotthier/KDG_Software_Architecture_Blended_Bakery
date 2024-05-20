package be.kdg.sa.bakery.senders;

import be.kdg.sa.bakery.config.RabbitTopology;
import be.kdg.sa.bakery.controller.dto.NewProductDto;
import be.kdg.sa.bakery.controller.dto.OrderDto;
import be.kdg.sa.bakery.controller.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

@RestController
public class RestSender {

    private static final Logger logger = LoggerFactory.getLogger(RestSender.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    public RestSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/Product")
    public void sendNewProduct(@RequestBody ProductDto productDto) throws JsonProcessingException {
        logger.debug("Trying to send new product message for product: {}", productDto.getName());
        rabbitTemplate.convertAndSend(RabbitTopology.NEW_PRODUCT_QUEUE, "NEW_PRODUCT_QUEUE",
                objectMapper.writeValueAsString(new NewProductMessage(productDto.getProductId(), productDto.getName(), productDto.getIngredients())));
        logger.info("Delivery message was successfully posted to the NEW_PRODUCT_QUEUE for product: {} ", productDto.getName());
    }

    @PostMapping("/ChangeProductState/{uuid}")
    public void sendChangeProductState(@PathVariable UUID uuid) throws JsonProcessingException {
        logger.debug("Trying to sendDelivery message for UUID: {}", uuid);

        rabbitTemplate.convertAndSend(RabbitTopology.PRODUCT_STATE_QUEUE, "PRODUCT_STATE_QUEUE",
                objectMapper.writeValueAsString(uuid));
        logger.info("Delivery message was successfully posted to the PRODUCT_STATE_QUEUE for UUID: {}", uuid);
    }

    @PostMapping("/ChangeProductState/{uuid}")
    public void sendOrderIngredients(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        //logger.debug("Trying to send Order ingredients message for order: {}", orderDto.getid);
        //rabbitTemplate.convertAndSend(RabbitTopology.PRODUCT_STATE_QUEUE, "PRODUCT_STATE_QUEUE",
        //        objectMapper.writeValueAsString(new OrderIngredientMessage (OrderDto.getId, orderDto.getTimestamp, orderDto.getIngredientIt, orderDto.getAmountOrder)));
        //logger.info("Delivery message was successfully posted to the PRODUCT_STATE_QUEUE for order: {}", orderDto.getid);
    }
}
