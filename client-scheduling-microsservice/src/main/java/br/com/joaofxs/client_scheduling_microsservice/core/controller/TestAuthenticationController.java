package br.com.joaofxs.client_scheduling_microsservice.core.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teste")
public class TestAuthenticationController {




    @GetMapping
    public ResponseEntity<String> testAuth(){
        return ResponseEntity.ok("Teste funcionando");
    }
}
