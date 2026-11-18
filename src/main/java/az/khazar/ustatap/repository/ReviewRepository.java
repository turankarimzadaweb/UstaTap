package az.khazar.ustatap.repository;

import az.khazar.ustatap.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrder_Master_Id(Long masterId);
    boolean existsByOrder_Id(Long orderId);
}