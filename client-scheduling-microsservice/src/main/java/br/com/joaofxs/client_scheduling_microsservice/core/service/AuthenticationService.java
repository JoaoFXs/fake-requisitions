package br.com.joaofxs.client_scheduling_microsservice.core.service;

import br.com.joaofxs.client_scheduling_microsservice.core.dto.AccessToken;
import br.com.joaofxs.client_scheduling_microsservice.core.exception.UserAlreadyExistException;
import br.com.joaofxs.client_scheduling_microsservice.core.model.User;
import br.com.joaofxs.client_scheduling_microsservice.core.dto.AuthRequest;
import br.com.joaofxs.client_scheduling_microsservice.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AccessToken register(User user, String role) {
        // Verifica se o usuário já existe
        if(userRepository.getByEmail(user.getEmail()) != null){
            throw new UserAlreadyExistException(user.getEmail() + " já cadastrado");
        }

        if (role.contains("user")) {
            user.setRoles(Set.of("USER"));
        } else {
            user.setRoles(Set.of("ADMIN"));
        }

        // Criptografa a senha antes de salvar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return jwtService.generateToken(user);
    }


    public AccessToken authenticate(AuthRequest request) {
        var user = userRepository.getByEmail(request.email());
        if(user == null){
            throw new UsernameNotFoundException(request.email() + " não encontrado");
        }
        if(passwordEncoder.matches(request.password(), user.getPassword())){
            return jwtService.generateToken(user);
        }
        return null;
    }
}