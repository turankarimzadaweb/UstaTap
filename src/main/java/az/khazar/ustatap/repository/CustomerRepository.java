package az.khazar.ustatap.repository;

import az.khazar.ustatap.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}