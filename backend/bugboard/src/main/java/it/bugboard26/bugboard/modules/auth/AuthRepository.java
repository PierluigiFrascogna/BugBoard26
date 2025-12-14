package it.bugboard26.bugboard.modules.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bugboard26.bugboard.entities.User;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID>  {
    
}
