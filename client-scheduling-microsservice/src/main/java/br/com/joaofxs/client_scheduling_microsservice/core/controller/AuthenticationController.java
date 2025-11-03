package br.com.joaofxs.client_scheduling_microsservice.core.controller;


import br.com.joaofxs.client_scheduling_microsservice.core.dto.AccessToken;
import br.com.joaofxs.client_scheduling_microsservice.core.dto.AuthRequest;

import br.com.joaofxs.client_scheduling_microsservice.core.model.User;

import br.com.joaofxs.client_scheduling_microsservice.core.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AccessToken> register(@RequestBody User request) {
        return new ResponseEntity<>(service.register(request, "user"), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<AccessToken> registerAdmin(@RequestBody User request) {
        return new ResponseEntity<>(service.register(request, "admin"), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AccessToken> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}