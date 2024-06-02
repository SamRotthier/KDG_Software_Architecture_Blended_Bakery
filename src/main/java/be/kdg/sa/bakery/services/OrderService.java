package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.dto.OrderDto;
import be.kdg.sa.bakery.controller.dto.OrderProductDto;
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
        logger.info("Adding new order with ID: {}", orderDto.getOrderId());
        Order order = new Order(orderDto.getOrderId(), orderDto.getAccountId());
        Order savedOrder = orderRepository.save(order);
        logger.info("Saved order successfully with ID: {}", savedOrder.getId());

        // Get all products from orderDto
        logger.info("Retrieve all products from order.");
        List<OrderProductDto> productsList = orderDto.getProducts();
        for (OrderProductDto product : productsList) {
            System.out.println("ID: " + product.getId() + ", ProductId: " + product.getProductId() + ", ProductId: " + product.getOrderId() + ", Quantity: " + product.getQuantity());
        }

        List<Product> products = productRepository.findAll();

        // Save all products in OrderProduct entities
        for (OrderProductDto productDto : orderDto.getProducts()) {
            Product product = products.stream().filter(p -> p.getProductId().equals((productDto.getProductId()))).findFirst().orElse(null);
            if(product != null){
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProduct(product);
                orderProduct.setOrder(savedOrder);
                orderProduct.setQuantity(productDto.getQuantity());
                orderProductRepository.save(orderProduct);
            } else{
                logger.warn("Product with ID {} was not found in the database.", productDto.getProductId());
            }
        }
        logger.info("Products saved successfully.");
    }

    @Transactional
    public List<Order> getAllOpenOrders() {
        logger.info("Fetching all open orders");
        return orderRepository.findByOrderStatusOrderByCreationTimestamp(OrderStatus.OPEN);
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
        logger.info("Adding delivered ingredients for order ID: {}", orderDto.getOrderId());
        //TODO
    }
}
