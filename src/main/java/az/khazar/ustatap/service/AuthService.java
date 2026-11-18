package az.khazar.ustatap.service;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.model.Customer;
import az.khazar.ustatap.model.Master;
import az.khazar.ustatap.model.User;
import az.khazar.ustatap.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Dto.UserResponse register(Dto.RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu email artıq qeydiyyatdan keçib");
        }

        User user;
        if ("MASTER".equals(request.role())) {
            Master master = new Master();
            master.setProfession(request.profession());
            master.setDistrict(request.district());
            master.setPrice(request.price());
            master.setDescription(request.description());
            user = master;
        } else {
            Customer customer = new Customer();
            customer.setAddress(request.address());
            user = customer;
        }

        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhone(request.phone());
        userRepository.save(user);

        return toResponse(user);
    }

    public Dto.UserResponse login(Dto.LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email və ya parol səhvdir"));

        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email və ya parol səhvdir");
        }
        return toResponse(user);
    }

    private Dto.UserResponse toResponse(User user) {
        String role = (user instanceof Master) ? "MASTER" : "CUSTOMER";
        return new Dto.UserResponse(user.getId(), user.getFullName(), user.getEmail(), role);
    }
}