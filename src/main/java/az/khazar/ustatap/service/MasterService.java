package az.khazar.ustatap.service;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.model.Master;
import az.khazar.ustatap.model.Review;
import az.khazar.ustatap.repository.MasterRepository;
import az.khazar.ustatap.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MasterService {

    private final MasterRepository masterRepository;
    private final ReviewRepository reviewRepository;

    public MasterService(MasterRepository masterRepository, ReviewRepository reviewRepository) {
        this.masterRepository = masterRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Dto.MasterResponse> getAll() {
        return masterRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Dto.MasterResponse getById(Long id) {
        Master master = masterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usta tapılmadı"));
        return toResponse(master);
    }

    private Dto.MasterResponse toResponse(Master m) {
        List<Review> reviews = reviewRepository.findByOrder_Master_Id(m.getId());
        double average = reviews.stream().mapToInt(Review::getStars).average().orElse(0.0);
        average = Math.round(average * 10) / 10.0;
        return new Dto.MasterResponse(m.getId(), m.getFullName(), m.getPhone(), m.getProfession(),
                m.getDistrict(), m.getPrice(), m.getDescription(), average, reviews.size());
    }
}