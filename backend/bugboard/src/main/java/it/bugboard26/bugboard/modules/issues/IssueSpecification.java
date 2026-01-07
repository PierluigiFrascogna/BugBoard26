package it.bugboard26.bugboard.modules.issues;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.enums.IssueState;
import it.bugboard26.bugboard.enums.IssueType;
import it.bugboard26.bugboard.enums.Priority;

public class IssueSpecification {
     public static Specification<Issue> hasProjectUuid(UUID projectUuid) {
        return (root, query, cb) -> projectUuid == null ? 
            cb.disjunction() : // Se manca l'UUID, non restituisce nulla (sicurezza)
            cb.equal(root.get("project").get("uuid"), projectUuid);
    }

    public static Specification<Issue> hasType(String type) {
        return (root, query, cb) -> type == null ? cb.conjunction() : cb.equal(root.get("type"), IssueType.valueOf(type.toUpperCase()));
    }

    public static Specification<Issue> hasPriority(String priority) {
        return (root, query, cb) -> priority == null ? cb.conjunction() : cb.equal(root.get("priority"), Priority.valueOf(priority.toUpperCase()));
    }

    public static Specification<Issue> hasStatus(String state) {
        return (root, query, cb) -> state == null ? cb.conjunction() : cb.equal(root.get("state"),  IssueState.valueOf(state.toUpperCase()));
    }
}
