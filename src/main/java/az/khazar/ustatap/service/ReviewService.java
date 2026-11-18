package az.khazar.ustatap.service;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.model.Order;
import az.khazar.ustatap.model.OrderStatus;
import az.khazar.ustatap.model.Review;
import az.khazar.ustatap.repository.OrderRepository;
import az.khazar.ustatap.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    public Review create(Dto.CreateReviewRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sifariş tapılmadı"));

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rəy yalnız tamamlanmış sifariş üçün yazıla bilər");
        }
        if (reviewRepository.existsByOrder_Id(order.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu sifariş üçün rəy artıq yazılıb");
        }
        if (request.stars() < 1 || request.stars() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ulduz 1 ilə 5 arasında olmalıdır");
        }

        Review review = new Review();
        review.setStars(request.stars());
        review.setComment(request.comment());
        review.setOrder(order);
        return reviewRepository.save(review);
    }

    public List<Review> getByMaster(Long masterId) {
        return reviewRepository.findByOrder_Master_Id(masterId);
    }
}