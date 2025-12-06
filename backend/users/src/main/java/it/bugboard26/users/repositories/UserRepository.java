package it.bugboard26.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import it.bugboard26.users.entities.User;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
}
