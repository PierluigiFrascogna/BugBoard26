package it.bugboard26.bugboard.repositories;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import it.bugboard26.bugboard.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Set<Project> findByUsers_uuid(UUID userUuid);
    
}
