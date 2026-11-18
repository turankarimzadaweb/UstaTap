package az.khazar.ustatap.controller;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.model.Order;
import az.khazar.ustatap.model.OrderStatus;
import az.khazar.ustatap.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestBody Dto.CreateOrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getByCustomer(@PathVariable Long customerId) {
        return orderService.getByCustomer(customerId);
    }

    @GetMapping("/master/{masterId}")
    public List<Order> getByMaster(@PathVariable Long masterId) {
        return orderService.getByMaster(masterId);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateStatus(id, status);
    }
}