package it.bugboard26.bugboard.modules.projects;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import it.bugboard26.bugboard.entities.Project;

@AllArgsConstructor
@Service
public class ProjectService {
    ProjectRepository projectRepository;

    public Optional<Project> getByUuid(UUID uuid) {
        return projectRepository.findById(uuid);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(String projectName) {
        Project project = new Project();
        project.setName(projectName);
        project.setCreatedAt(LocalDate.now());
        return projectRepository.save(project);
    }
}
