package it.bugboard26.bugboard.dtos;

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

    public ProjectResponse(Project project) {
        this.uuid = project.getUuid();
        this.name = project.getName();
        this.createdAt = project.getCreatedAt();
    }
}
