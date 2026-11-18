package az.khazar.ustatap.repository;

import az.khazar.ustatap.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByMasterId(Long masterId);
}