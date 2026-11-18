package az.khazar.ustatap.dto;

public class Dto {

    public record MasterResponse(Long id, String fullName, String phone, String profession,
                                 String district, Double price, String description,
                                 double averageRating, int reviewCount) {
    }

    public record RegisterRequest(String role, String fullName, String email, String password,
                                  String phone, String profession, String district,
                                  Double price, String description, String address) {
    }

    public record LoginRequest(String email, String password) {
    }

    public record UserResponse(Long id, String fullName, String email, String role) {
    }

    public record CreateOrderRequest(Long customerId, Long masterId, String description) {
    }

    public record CreateReviewRequest(Long orderId, int stars, String comment) {
    }
}