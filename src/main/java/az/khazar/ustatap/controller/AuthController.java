package az.khazar.ustatap.controller;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Dto.UserResponse register(@RequestBody Dto.RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Dto.UserResponse login(@RequestBody Dto.LoginRequest request) {
        return authService.login(request);
    }
}