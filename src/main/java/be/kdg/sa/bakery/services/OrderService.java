package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.dto.OrderDto;
import be.kdg.sa.bakery.domain.Enum.OrderStatus;
import be.kdg.sa.bakery.domain.Order;
import be.kdg.sa.bakery.domain.OrderProduct;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.repositories.OrderProductRepository;
import be.kdg.sa.bakery.repositories.OrderRepository;
import be.kdg.sa.bakery.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;
    private ProductRepository productRepository;

    private BakingService bakingService;

    public OrderService(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductRepository productRepository, BakingService bakingService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.bakingService = bakingService;
    }

    public void addOrder(OrderDto orderDto) {
        logger.info("Adding new order with ID: {}", orderDto.getId());
        Order order = new Order(orderDto.getId(), orderDto.getAccountId(), orderDto.getCreationDate());
        Order savedOrder = orderRepository.save(order);
        List<Product> productList = productRepository.findByProductIdIn(orderDto.getProductQuantities().keySet().stream().toList());
        orderProductRepository.saveAll(productList.stream().map(p -> new OrderProduct(p, savedOrder, orderDto.getProductQuantities().get(p.getProductId()))).toList());
    }

    @Transactional
    public List<Order> getAllOpenOrders() {
        logger.info("Fetching all open orders");
        return orderRepository.findByOrderStatusOrderByCreationTimestamp(OrderStatus.PENDING);
    }

    public void bakeOrder(UUID id) {
        logger.info("Baking preparations for order ID: {}", id);
        bakingService.bakingPreparationsOneOrder(id);
    }

    public void bakeAllOpenOrders() {
        logger.info("Baking preparations for all open orders");
        bakingService.bakingPreparations();
    }

    public void addDeliveredIngredients(OrderDto orderDto) {
        logger.info("Adding delivered ingredients for order ID: {}", orderDto.getId());
        //TODO
    }
}
