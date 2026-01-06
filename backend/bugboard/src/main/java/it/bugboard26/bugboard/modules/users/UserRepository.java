package it.bugboard26.bugboard.modules.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bugboard26.bugboard.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByUuidIn(List<UUID> userIds);
    
}
