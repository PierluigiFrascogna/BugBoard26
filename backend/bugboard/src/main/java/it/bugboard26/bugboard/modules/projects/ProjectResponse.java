package it.bugboard26.bugboard.modules.projects;

import java.time.LocalDate;
import java.util.UUID;

import it.bugboard26.bugboard.entities.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {
    private UUID uuid;
    private String name;
    private LocalDate createdAt;

    public static ProjectResponse map(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.uuid = project.getUuid();
        response.name = project.getName();
        response.createdAt = project.getCreatedAt();
        return response;
    }
}
