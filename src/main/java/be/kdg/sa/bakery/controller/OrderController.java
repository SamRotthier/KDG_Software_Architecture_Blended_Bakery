package be.kdg.sa.bakery.controller;

import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/open")
    public String openOrders(Model model){
        model.addAttribute("orders", orderService.getAllOpenOrders());
        return "orders/orders";
    }

    @PostMapping("/bake/{id}")
    public String bakeOrder(@PathVariable UUID id){
        orderService.bakeOrder(id);
        return "redirect:/orders/open";
    }

    @PostMapping("/bake/all")
    public String bakeAllOrders(){
        orderService.bakeAllOpenOrders();
        return "redirect:/orders/open";

    }
}
