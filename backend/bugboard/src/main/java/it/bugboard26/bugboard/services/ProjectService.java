package it.bugboard26.bugboard.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.repositories.ProjectRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProjectService {
    
    ProjectRepository projectRepository;

    public Project getByUuid(UUID uuid) {
        return projectRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    public List<Project> getByUserUuid(UUID userUuid) {
        return projectRepository.findByUsersUuid(userUuid);
    }
}
