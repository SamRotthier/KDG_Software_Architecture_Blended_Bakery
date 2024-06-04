package be.kdg.sa.bakery.senders;

import be.kdg.sa.bakery.config.RabbitTopology;
import be.kdg.sa.bakery.controller.dto.ProductIngredientDto;
import be.kdg.sa.bakery.domain.Order;
import be.kdg.sa.bakery.domain.OrderProduct;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.domain.ProductIngredient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        logger.info("Send new product message for product: {}", product.getName());
        logger.info("Ingredient ID test: {}", product.getIngredients().stream().findFirst().orElse(new ProductIngredient()).getIngredient().getId());
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "new-product-warehouse-queue",
                (new NewProductMessage(product.getProductId(), product.getName(), product.getIngredients().stream().map(i -> new ProductIngredientDto(i.getIngredient().getId(), i.getIngredient().getName(), i.getQuantity())).toList())));
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "new-product-client-queue",
                (new NewProductMessage(product.getProductId(), product.getName())));
        logger.info("Delivery message was successfully posted to the NEW_PRODUCT_QUEUE for product: {} ", product.getName());
    }


    public void sendChangeProductState(UUID uuid) {
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "product-state-queue", uuid);
        logger.info("The change of the product state was successfully posted to the PRODUCT_STATE_QUEUE for UUID: {}", uuid);
    }

    public void sendOrderIngredients(Order order) {
        logger.info("Trying to send the order ingredients message for order ID: {}", order.getId());

        //convert list product to map ingredients with quantities
        Map<UUID, Integer> ingredients = extractIngredients(order.getProducts());

        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "order-ingredient-queue", (new OrderIngredientsMessage (order.getId(), order.getBakeStartTimestamp(), ingredients)));
        logger.info("Order ingredients message was successfully posted to the ORDER_INGREDIENT_QUEUE for order: {}", order.getId());
    }

    public void sendConfirmation(){
        logger.info("Sending confirmation message to warehouse");
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "confirm-order-ingredient-queue", new ConfirmationMessage("Confirmed: Ingredients received successfully."));
    }

    public Map<UUID, Integer> extractIngredients(List<OrderProduct> orderProducts) {
        Map<UUID, Integer> ingredientQuantityMap = new HashMap<>();

        orderProducts.forEach(orderProduct -> {
            Product product = orderProduct.getProduct();
            int productQuantity = orderProduct.getQuantity();

            product.getIngredients().forEach(ingredient -> {
                UUID ingredientId = ingredient.getIngredient().getId();
                int ingredientQuantity = ingredient.getQuantity();
                int totalIngredientQuantity = productQuantity * ingredientQuantity;

                ingredientQuantityMap.put(ingredientId, ingredientQuantityMap.getOrDefault(ingredientId, 0) + totalIngredientQuantity);
            });
        });
        return ingredientQuantityMap;
    }
}
