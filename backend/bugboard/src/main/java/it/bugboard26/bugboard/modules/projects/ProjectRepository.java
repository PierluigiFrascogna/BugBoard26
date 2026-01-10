package it.bugboard26.bugboard.modules.projects;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import it.bugboard26.bugboard.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    
}
