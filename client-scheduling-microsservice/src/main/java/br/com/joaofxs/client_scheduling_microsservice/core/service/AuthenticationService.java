package br.com.joaofxs.client_scheduling_microsservice.core.service;

import br.com.joaofxs.client_scheduling_microsservice.core.dto.AccessToken;
import br.com.joaofxs.client_scheduling_microsservice.core.model.User;
import br.com.joaofxs.client_scheduling_microsservice.core.dto.AuthRequest;
import br.com.joaofxs.client_scheduling_microsservice.core.dto.AuthResponse;
import br.com.joaofxs.client_scheduling_microsservice.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;



    public AccessToken register(User user) {
        // Criptografa a senha antes de salvar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return jwtService.generateToken(user);
    }

    public AccessToken authenticate(AuthRequest request) {

        var user = userRepository.getByEmail(request.email());
        if(user == null){
            return null;
        }

        if(passwordEncoder.matches(request.password(), user.getPassword())){
            return jwtService.generateToken(user);
        }
        return null;

    }
}