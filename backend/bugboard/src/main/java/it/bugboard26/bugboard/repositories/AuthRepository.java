package it.bugboard26.bugboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bugboard26.bugboard.entities.User;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID>  {
    
}
