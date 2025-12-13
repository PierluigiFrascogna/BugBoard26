package it.bugboard26.bugboard.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bugboard26.bugboard.entities.IssueEvent;

public interface EventRepository extends JpaRepository<IssueEvent, UUID> {
    List<IssueEvent> findByIssueUuid(UUID uuid_issue);
    
}
