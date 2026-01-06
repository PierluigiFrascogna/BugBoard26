package it.bugboard26.bugboard.modules.issues;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.bugboard26.bugboard.entities.Issue;

public interface IssueRepository extends JpaRepository<Issue, UUID>, JpaSpecificationExecutor<Issue> {
    List<Issue> findByProjectUuid(UUID projectUuid);
}
