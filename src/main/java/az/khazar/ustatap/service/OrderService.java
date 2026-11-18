package az.khazar.ustatap.service;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.model.Customer;
import az.khazar.ustatap.model.Master;
import az.khazar.ustatap.model.Order;
import az.khazar.ustatap.model.OrderStatus;
import az.khazar.ustatap.repository.CustomerRepository;
import az.khazar.ustatap.repository.MasterRepository;
import az.khazar.ustatap.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MasterRepository masterRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        MasterRepository masterRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.masterRepository = masterRepository;
    }

    public Order create(Dto.CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sifariş yalnız müştəri hesabı ilə verilə bilər"));
        Master master = masterRepository.findById(request.masterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usta tapılmadı"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setMaster(master);
        order.setDescription(request.description());
        return orderRepository.save(order);
    }

    public List<Order> getByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getByMaster(Long masterId) {
        return orderRepository.findByMasterId(masterId);
    }

    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sifariş tapılmadı"));

        OrderStatus current = order.getStatus();
        boolean allowed = (current == OrderStatus.PENDING && newStatus == OrderStatus.ACCEPTED)
                || (current == OrderStatus.ACCEPTED && newStatus == OrderStatus.COMPLETED);

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status bu qaydada dəyişdirilə bilməz");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}