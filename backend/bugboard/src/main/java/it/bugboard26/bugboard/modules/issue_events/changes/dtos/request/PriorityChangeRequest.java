package it.bugboard26.bugboard.modules.issue_events.changes.dtos.request;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.PriorityChange;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.ChangeType;
import it.bugboard26.bugboard.enums.Priority;
import lombok.Getter;

@Getter
public class PriorityChangeRequest implements ChangeRequest {
    private ChangeType type = ChangeType.PRIORITY;
    private Priority newPriority;

    @Override
    public Change mapToEntity(Issue issue, User author) {
        PriorityChange change = new PriorityChange();
        change.setOldPriority(issue.getPriority());
        change.setNewPriority(this.newPriority);
        change.setIssue(issue);
        change.setAuthor(author);
        return change;
    }
    
}
