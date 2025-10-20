package br.com.joaofxs.client_scheduling_microsservice.core.repository;


import br.com.joaofxs.client_scheduling_microsservice.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
