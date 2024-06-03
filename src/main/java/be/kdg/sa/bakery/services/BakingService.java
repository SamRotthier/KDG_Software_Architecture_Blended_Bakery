package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.domain.Enum.OrderStatus;
import be.kdg.sa.bakery.domain.Order;
import be.kdg.sa.bakery.repositories.OrderRepository;
import be.kdg.sa.bakery.senders.RestSender;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class BakingService {
    private static final Logger logger = LoggerFactory.getLogger(BakingService.class);
    private final OrderRepository orderRepository;
    private final RestSender restSender;

    public BakingService(OrderRepository orderRepository, RestSender restSender) {
        this.orderRepository = orderRepository;
        this.restSender = restSender;
    }

    @Scheduled(cron = "${be.kdg.cron}")
    @Transactional
    public void bakingPreparations() {
        logger.info("Starting the baking process");
        List<Order> openOrders = orderRepository.findByOrderStatusOrderByCreationTimestamp(OrderStatus.OPEN);

        if (openOrders == null || openOrders.isEmpty()) {
            logger.warn("No open orders found for baking.");
            return;
        }

        for (Order order : openOrders) {
            logger.info("Start baking process for open order with id: {}", order.getId());

            /* Map<UUID, Integer> ingredients = new HashMap<>();
            order.getProducts().forEach(p -> {
                p.getProduct().getIngredients().forEach(i -> {
                    ingredients.put(i.getIngredient().getId(), i.getQuantity() * p.getQuantity());
                });
            });*/
            logger.info("Send request for ingredients to warehouse: {}", order.getId());
            restSender.sendOrderIngredients(order);

            logger.info("Update order information");
            order.setBakeStartTimestamp(Instant.now());
            order.setOrderStatus(OrderStatus.AWAITING_INGREDIENTS);
            orderRepository.save(order);
        }
    }


    public void bakingPreparationsOneOrder(UUID id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            logger.info("Start baking process for the open order with id: {}", order.getId());

            /* Map<UUID, Integer> ingredientsOrder = new HashMap<>();

            order.getProducts().forEach(p -> {
                p.getProduct().getIngredients().forEach(i -> {
                    ingredientsOrder.put(i.getIngredient().getId(), i.getQuantity() * p.getQuantity());
                });
            });*/
            logger.info("Send request for ingredients to warehouse: {}", order.getId());
            restSender.sendOrderIngredients(order);

            logger.info("Update order information");
            order.setBakeStartTimestamp(Instant.now());
            order.setOrderStatus(OrderStatus.AWAITING_INGREDIENTS);
            orderRepository.save(order);

        } else{
            logger.error("Order with Id {} was not found", id);
        }
    }

    @Transactional
    public void finishBakeOrder(UUID id){
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setBakeFinishTimestamp(Instant.now());
            order.setOrderStatus(OrderStatus.DONE);
            orderRepository.save(order);
        } else{
            logger.error("Order with Id {} was not found", id);
        }

    }
}
