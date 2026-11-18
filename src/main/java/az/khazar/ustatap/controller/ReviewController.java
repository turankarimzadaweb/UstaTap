package az.khazar.ustatap.controller;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.model.Review;
import az.khazar.ustatap.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Review create(@RequestBody Dto.CreateReviewRequest request) {
        return reviewService.create(request);
    }

    @GetMapping("/master/{masterId}")
    public List<Review> getByMaster(@PathVariable Long masterId) {
        return reviewService.getByMaster(masterId);
    }
}