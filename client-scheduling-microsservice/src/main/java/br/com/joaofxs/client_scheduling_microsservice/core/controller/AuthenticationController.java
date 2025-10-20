package br.com.joaofxs.client_scheduling_microsservice.core.controller;


import br.com.joaofxs.client_scheduling_microsservice.core.dto.AccessToken;
import br.com.joaofxs.client_scheduling_microsservice.core.dto.AuthRequest;
import br.com.joaofxs.client_scheduling_microsservice.core.dto.AuthResponse;
import br.com.joaofxs.client_scheduling_microsservice.core.model.User;
import br.com.joaofxs.client_scheduling_microsservice.core.repository.UserRepository;
import br.com.joaofxs.client_scheduling_microsservice.core.service.AuthenticationService;
import br.com.joaofxs.client_scheduling_microsservice.core.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTOs (Data Transfer Objects) para as requisições e respostas



@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AccessToken> register(@RequestBody User request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}